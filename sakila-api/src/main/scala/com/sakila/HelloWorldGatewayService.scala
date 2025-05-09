package com.sakila

import com.twitter.finagle.{Service, http}
import com.twitter.finagle.http.{Request, Response}
import com.twitter.util.Future

class HelloWorldGatewayService extends Service[Request, Response] {

  override def apply(request: Request): Future[Response] = {
    val response = Response(http.Status.Ok)
    response.setContentString("Hello world")
    Future {
      response
    }
  }
}
