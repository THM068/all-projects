package controllers

import models.Customers
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json}
import play.api.mvc.{BaseController, ControllerComponents, Request}
import repositories.CustomerRepository

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class CustomerController @Inject()(val controllerComponents: ControllerComponents, customerRepository: CustomerRepository)
                                  (implicit val ec: ExecutionContext) extends BaseController {

  def createCustomer() = Action.async(parse.json) { implicit request: Request[JsValue] =>
    Json.fromJson[Customers](request.body).fold(
      errors => {
        Future.successful(BadRequest(Json.obj("status" -> "error", "message" -> JsError.toJson(errors))))
      },
      customer => {
        customerRepository.create(customer).map { createdCustomer =>
          Created(Json.toJson(createdCustomer))
        }
      }
    )

  }

}
