package com.bill.tracker.server.routes
import com.bill.tracker.model._
import com.bill.tracker.repository.{CategoryRepository, ExpensePeriodRepository}
import com.bill.tracker.server.{AutowireJwtService, JwtService}
import com.bill.tracker.server.routes.ServerUtils.parseBody
import com.bill.tracker.views.Page
import zio._
import zio.http.Middleware.bearerAuth
import zio.http.template.div
import zio.http.{Method, Request, Response, Routes, Status, handler, long, string}
import zio.json.EncoderOps

case class CategoryRoutes(categoryRepository: CategoryRepository, jwtService: JwtService, expensePeriodRepository: ExpensePeriodRepository) {

  val create = Method.POST / "category" / "create" -> handler {
    for {
      _ <- categoryRepository.create()
    } yield Response.ok
  }
  val categories = Method.GET / "category" -> handler { (request: Request) =>
    (for {
      _ <- Console.printLine(Thread.currentThread().getName).fork
      _ <- Requesthelper.getUserDetails(request).provide(AutowireJwtService.layer)

      categoryList <- categoryRepository.findAll()
      categoryMapperList = CategoryMapperList(categoryMapperList = categoryList.map(_.toCategoryMapper()))
    } yield(Response.json(categoryMapperList.toJson))).catchSome(catchException)
  }

  val findByName = Method.GET / "category" / "name" / string("name") -> handler { (name: String, _: Request) =>
    (for {
      categoryList <- categoryRepository.findByName(name)
      categoryMapperList = CategoryMapperList(categoryMapperList = categoryList.map(_.toCategoryMapper()))
    } yield Response.json(categoryMapperList.toJson)).catchSome(catchException)
  }

  val deleteCategory = Method.DELETE / "category" / long("id") -> handler { (id: Long, _: Request) =>
    (for {
      _ <- categoryRepository.deleteCategory(id)
    } yield Response.status(Status.Ok)).catchSome(catchException)
  }

  val addCategory = Method.POST / "category" -> handler { (request: Request) =>
    (for {
        categoryDTO <- parseBody[CategoryDTO](request)
        id <- categoryRepository.createCategory(categoryDTO.toCategory())
      } yield Response.json(CategoryDTO(Some(id), categoryDTO.name).toJson).status(Status.Created)).catchSome(catchException)
  }

  val getCategory = Method.GET / "category" / long("id") -> handler { (id: Long, r : Request) =>
    (for {
      categoryList <- categoryRepository.getCategory(id)
      categoryOption = categoryList.headOption
    } yield {
      categoryOption match {
        case Some(category) =>
          Response.json(category.toJson)
        case _ =>
          Response.text("Missing category").status(Status.NotFound)
      }
    }).catchSome(catchException)
  }

  val htmlPage = Method.GET / "html" -> handler {
    Response.html(Page.main)
  }

  val htmlName = Method.GET / "bunny" -> handler {
    Response.html(div("I am a bunny"))
  }

  def catchException: PartialFunction[Throwable, ZIO[Any, Throwable, Response]] = {
    case e: Exception =>
      ZIO.succeed(
        Response.json(
          Message(e.getMessage, "category-repository").toJson)
          .status(Status.BadRequest)
      )
    case AppError.MissingBodyError =>
      ZIO.succeed(
        Response.json(
          Message("Missing body in request", "category-repository").toJson)
          .status(Status.BadRequest)
      )
    case AppError.JsonDecodingError(message) =>
      ZIO.succeed(
        Response.json(
          Message(message, "category-repository").toJson)
          .status(Status.BadRequest)
      )
  }
  val apps = Routes(categories,  findByName, getCategory, addCategory, deleteCategory, create, htmlPage, htmlName)
    .handleError(HandleErrors.handle)
    .toHttpApp

}

object CategoryRoutes {
  val layer: ZLayer[CategoryRepository with JwtService with ExpensePeriodRepository, Nothing, CategoryRoutes] =
    ZLayer.fromFunction(CategoryRoutes.apply _)
}
