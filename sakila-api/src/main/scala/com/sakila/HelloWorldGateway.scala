package com.sakila
import com.twitter.finagle.Service
import com.twitter.finagle.http.path.{/, Root}
import com.twitter.finagle.http.service.{NotFoundService, RoutingService}
import com.twitter.finagle.http.{Method, Request, Response}

class HelloWorldGateway extends ServerGateway {

  val helloWorldGatewayService = new HelloWorldGatewayService()

  override def service: Service[Request, Response] = {
    RoutingService.byMethodAndPathObject[Request] {
      case (Method.Get, Root / "hello-world" ) =>
        helloWorldGatewayService
      case _ => NotFoundService
    }
  }
}
