package controllers

import config.PetShopConfig
import models._
import play.api.Logging
import play.api.data.Form
import play.api.data.Forms.{mapping, nonEmptyText, number}
import play.api.libs.json._
import play.api.mvc.Results.NotFound
import play.api.mvc._

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject._
import scala.concurrent.{ExecutionContext, Future}

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents,
                               val loggingAction: LoggingAction,
                               val interceptor: Interceptor,
                               val customerEnabledAction: CustomerEnabledAction,
                               val userAction: UserAction,
                               petShopConfig: PetShopConfig) (implicit ec: ExecutionContext) extends BaseController {

   val  customerForm = Form(
     mapping(
       "name" -> nonEmptyText,
        "age" -> number(min = 0, max = 100)
     )(CustomerForm.apply)(CustomerForm.unapply)
   )
  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    val name = "World"

    val sessionMap = request.session.data ++ Map("name" -> "Thando", "age" -> "25")
    Redirect(routes.HomeController.productListing(), MOVED_PERMANENTLY).withSession(sessionMap.toSeq:_*)
  }

  def hello() = Action { implicit request: Request[AnyContent] =>
    Ok("Hello, World!")

  }

  def productListing() = Action { implicit request: Request[AnyContent] =>
    val name = request.session.get("name")
    println(s"Name: $name")
    val products = ProductService.findAll()
    Ok(views.html.productList(products))
  }

  def productSpecificListing(count: Int) = Action { implicit request: Request[AnyContent] =>
    Ok(s"Product Specific Listing: $count")
  }

  def time() = Action {implicit request: Request[AnyContent] =>
    val time = LocalDateTime.now
    Ok(s"the time is ${time.toLocalTime.format(DateTimeFormatter.ofPattern("HH:mm"))}")
  }

  def jsonReturn() = Action { implicit request: Request[AnyContent] =>
     Ok(
       JsObject(
         Seq(
           "name" -> JsString("Thando"),
           "age"  -> JsNumber(43),
           "likes" -> JsArray(
             Seq(
               JsString("Scala"),
               JsString("Coffee"),
               JsString("Pianos")
             )
           ),
           "dislikes" -> JsNull
         )
       )
     )
  }

  def elegantJsonReturn() = Action { implicit request: Request[AnyContent] =>
    Ok(
      Json.obj(
        "name" -> "Thando",
        "age"-> 23,
        "likes" -> Json.arr(
          "Scala",
          "Coffee",
          "Pianos"
        ),
        "dislikes" -> JsNull
      )
    )

  }

  def parseJson() = Action { request: Request[AnyContent] =>
    request.body.asJson match {
      case Some(json) =>
        Ok(
          Json.obj(
            "message" -> "The request contained JSON data",
            "data" -> json
          )
        )
      case None =>
      Ok(Json.obj("message" -> "The request contained no JSON data"))
    }
  }

  def addCustomer() = (userAction andThen customerEnabledAction andThen interceptor andThen loggingAction).async(parse.json) { implicit request: Request[JsValue] =>
    val customer = customerForm.bindFromRequest.value
    println(s"isWeb: ${petShopConfig.isWeb}")
    println(s"host: ${petShopConfig.host}")
    println(s"port: ${petShopConfig.port}")
    println(s"regions: ${petShopConfig.regions}")
    println(s"customer config: ${petShopConfig.customerConfig}")
    val userRequest = request.asInstanceOf[UserRequest[JsValue]]
    println(s"User: ${userRequest.user}")
    printme()
    customer match {
      case Some(customer) => println(customer)
        Future.successful(Ok("Customer added"))
      case None => Future.successful(BadRequest("Invalid customer"))
    }

  }

  private def printme()(implicit request: Request[JsValue]) = {
    println(s"This is a print statement ${request.body}" )
  }

  def createCustomer() = interceptor { implicit request: Request[AnyContent] =>

    Ok("Create customer form")
  }

  def logMessage() = Action { implicit request: Request[AnyContent] =>
    val message = "This is a log message"
    val log = play.api.Logger(getClass)
    log.info(message)
    Ok("Log message sent")
  }

  def showPerson() = Action { implicit request: Request[AnyContent] =>
    import models.Model.personFormat

   val p =  Json.fromJson[Person](Json.obj(
      "name" -> "Eric Wimp",
      "address" -> Json.obj(
        "number" -> 29,
        "street" -> "Acacia Road"
      )
    ))
    p
    val person = Person("Thando", Address(1, "Main Street"))
    Ok(Json.toJson(person))
  }
}

class Interceptor @Inject()(val parser: BodyParsers.Default)(implicit val executionContext: ExecutionContext)  extends ActionBuilder[Request, AnyContent] {

  override def invokeBlock[A](request: Request[A], block: Request[A] => Future[Result]): Future[Result] = block(request)

}

class LoggingAction @Inject()(val parser: BodyParsers.Default)(implicit val executionContext: ExecutionContext) extends ActionBuilder[Request, AnyContent] with Logging {


  override def invokeBlock[A](request: Request[A], block: Request[A] => Future[Result]): Future[Result] = {
    logger.info("Calling action")
    println("***************")
    block(request)
  }
}

class CustomerEnabledAction @Inject()(val parser: BodyParsers.Default, petShopConfig: PetShopConfig)(implicit val executionContext: ExecutionContext) extends ActionBuilder[Request, AnyContent] with Logging {

  override def invokeBlock[A](request: Request[A], block: Request[A] => Future[Result]): Future[Result] = {
    petShopConfig.customerEnabled match {
      case true =>
        block(request)
      case false =>
        logger.info("Customer is not enabled")
        Future.successful(NotFound(("Customer is not enabled")))
    }
  }

}

class UserAction @Inject() (val parser: BodyParsers.Default)(implicit val executionContext: ExecutionContext) extends ActionBuilder[UserRequest, AnyContent] {

  override def invokeBlock[A](request: Request[A], block: UserRequest[A] => Future[Result]): Future[Result] = {
    val user = User("1","thando.mafela@example.com")
    val userRequest = UserRequest(user, request)
    block(userRequest)
  }
}




