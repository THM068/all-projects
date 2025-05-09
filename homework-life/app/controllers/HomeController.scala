package controllers

import akka.actor.ActorSystem
import akka.pattern.ask
import akka.util.Timeout
import models.Person._
import models.{HelloActor, Person, SayHello}
import play.api.libs.json._
import play.api.mvc._

import javax.inject._
import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents, val actorSystem: ActorSystem)(implicit ec: ExecutionContext) extends BaseController {

  val helloActor = actorSystem.actorOf(HelloActor.props, "Hello-Actor")
  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def todo() = Action { implicit request =>
    val name = s"Todo List for the Day ${request.session.get("username").getOrElse("")}"
    val todoList = List("Go to Shop", "Buy Bread", "Feed the cat")
    val todoHeader = <h2 style="color: blue">Todo List shown belowe</h2>
    Ok(views.html.todo(todoList, name, todoHeader))
  }

  def login() =  Action { implicit request =>
    Ok(views.html.login())
  }

  def validateLogin() = Action { request =>
    val postVals = request.body.asFormUrlEncoded
    postVals.map { args =>
      val username = args("username").head
      val password = args("password").head
      Redirect(routes.HomeController.todo()).withSession("username" -> "Thomas Mafela").flashing("color" -> "blue")
    }.getOrElse(Redirect(routes.HomeController.login()))
  }

  def showJson() = Action { implicit request: Request[AnyContent] =>
    val person = List(Person(name = "Thando", lastName = s"Mafela "))
    Ok(Json.toJson(person))
  }

  def createPerson() = Action(parse.json) { implicit  request =>
    val personResult = request.body.validate[Person]

    personResult.fold(
      error => {
        BadRequest(Json.obj("status" -> "error", "message" -> JsError.toJson(error)))
      },
      person => {
        Ok(Json.toJson(person))
      }
    )
  }

  def askpattern(name: String)= Action.async {
    implicit val timeout: Timeout = 5.seconds
    (helloActor ? SayHello(name)).mapTo[String].map { message =>
      Ok(message)
    }

  }


}

