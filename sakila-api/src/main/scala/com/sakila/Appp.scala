package com.sakila

import com.twitter.finagle.{Http, Service}
import com.twitter.finagle.http.{Method, Request, Response}
import com.twitter.util.Future

object Appp {
  def main(args: Array[String]): Unit = {
    val service= Http.newService("localhost:9090")
    val request = Request()
    request.method = Method.Get
    request.uri = "/hello-world"

    val response = service(request)
    response.onSuccess { resp => println(resp.getContentString())}
    response.onFailure(fn => println(fn.toString))
    Thread.sleep(5000)


  }
}