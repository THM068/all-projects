package controllers

import models.{LoginData, RegisterData}
import play.api.data.Forms.mapping
import play.api.data.format.Formats._
import play.api.data.{Form, Forms}
import play.api.mvc.{MessagesAbstractController, MessagesControllerComponents}

import javax.inject.Inject
object LoginValidators {
  val loginForm = Form(
    mapping(
      "Username" -> Forms.nonEmptyText,
      "Password" -> Forms.nonEmptyText
    )
    (LoginData.apply)(LoginData.unapply))

  val registerForm = Form(
    mapping(
    "name" -> Forms.nonEmptyText,
    "username" -> Forms.nonEmptyText,
      "email" -> Forms.email,
      "password" -> Forms.nonEmptyText
    )(RegisterData.apply)(RegisterData.unapply))
}
class LoginController @Inject()(override val controllerComponents: MessagesControllerComponents) extends MessagesAbstractController(controllerComponents) {
  import LoginValidators._
  def login() = Action { implicit request =>
    Ok(views.html.loginForm(loginForm))
  }

  def validateLogin() = Action { implicit request =>
    val formWithErrors = { }
    loginForm.bindFromRequest().fold(
      formWithErrors => BadRequest(views.html.loginForm(formWithErrors)),
      loginData => {
        println(loginData.username)
        println(loginData.password)
        Redirect(routes.HomeController.todo()).flashing("username" -> loginData.username)
      }

    )
  }

  def registration = Action { implicit  request =>
    Ok(views.html.registration(registerForm))
  }

  def validateRegistration() = Action { implicit  request =>
    Ok("Validate registratiom")
  }

}


//val userForm = Form(
//  mapping(
//    "name" -> Forms.of[String],
//    "age" -> of[Int],
//    "email" -> of[String]
//  )(User.apply)(User.unapply)
//)