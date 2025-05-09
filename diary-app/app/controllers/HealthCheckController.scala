package controllers

import akka.actor.{Actor, ActorSystem, Props}
import model.AppStatus._
import model.CategoryMapperList._
import model.{AppStatus, CategoryMapperList, Error}
import model.Error._
import model.repository.CategoryRepository
//import model.repository.CategoryRepository
import play.api.inject.ApplicationLifecycle
import play.api.libs.json.Json
import play.api.mvc._

import javax.inject._
import scala.concurrent.{ExecutionContext, Future}

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HealthCheckController @Inject()(val controllerComponents: ControllerComponents,
                                      lifecycle: ApplicationLifecycle,
                                      categoryRepository: CategoryRepository,
                                      actorSystem: ActorSystem )(implicit ec: ExecutionContext) extends BaseController {
   lifecycle.addStopHook { () => Future.successful(println("Closing"))}
   val helloActor = actorSystem.actorOf(HelloActor.props(), "hello-actor")
  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(Json.toJson(AppStatus(status = "ok")))
  }

  def status() = Action { implicit request: Request[AnyContent] =>
    val body = request.body.asJson

    body match {
      case Some(json) => Ok(json).withCookies()
      case _ => BadRequest(Json.toJson(Error(message = "Bad request", code = 400)))
    }
  }

  def cats()= Action.async { implicit request =>

    helloActor ! "Hello"
    for {
      categoryList <- categoryRepository.findAll()
      categoryMapperList = CategoryMapperList(categoryMapperList = categoryList.map(_.toCategoryMapper()))
    } yield Ok(Json.toJson(categoryMapperList))
  }

  class HelloActor extends Actor {
    override def receive: Receive = {
      case "Hello" => println(s"Hello world ...${context.self.path}")
      case _ => println("Default")
    }
  }

  object HelloActor {
    def apply() = new HelloActor()

    def props()= Props(HelloActor())
  }

}
