package controllers

import play.api.mvc.{BaseController, ControllerComponents}

import javax.inject.{Inject, Singleton}
@Singleton
class StockController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

}
