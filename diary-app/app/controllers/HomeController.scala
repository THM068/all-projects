package controllers
import model.LoginData
import javax.inject._
import play.api._
import play.api.data.{Form, Forms}
import play.api.data.Forms.mapping
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val config: Configuration, val controllerComponents: ControllerComponents) extends BaseController {

  val loginForm = Form(
    mapping(
      "name" -> Forms.nonEmptyText,
      "username" -> Forms.nonEmptyText
    )
    (LoginData.apply)(LoginData.unapply))
  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    println(config.get[String]("database.name"))
    Ok(views.html.index())
  }

  def storyForm() =  Action { implicit request: Request[AnyContent] =>
    Ok(views.html.showForm(loginForm))
  }

  def submitStory() = Action { implicit request: Request[AnyContent] =>
    println("Submit story ....")
    loginForm.bindFromRequest().fold(
      error =>  BadRequest(views.html.showForm(error)),
      result => {
       Redirect(routes.HomeController.index()).flashing("name" -> result.name, "username" -> result.username)
      }
    )

  }
}
