package com.crm.middleware

import zio.test.{ZIOSpecDefault, suite, test, _}
import zio._
import zio.http._
object HtmxMiddlewareSpec extends ZIOSpecDefault {

  val routes = Routes(
    Method.GET / "hello" / "world" -> handler { Response.text("Hey there!")  } @@ HtmxMiddleware.hxRequest(),
  )

  def spec = suite("HtmxMiddleware suite")(
    hxTrigger_suite
  )

  def hxTrigger_suite = suite("Test for Hxtrigger middleware")(
    test("Request with HX-Request header true should return response") {
      for {
        client           <- ZIO.service[Client]
        _                <- TestClient.addRoutes {
          routes
        }
        helloResponse    <- client.batched(Request.get(URL.root / "hello" / "world").addHeader(Header.Custom("HX-Request", "true")))
        helloBody        <- helloResponse.body.asString
      } yield assertTrue(helloBody == "Hey there!")
    }.provide(TestClient.layer),

    test("Request without HX-Request header should return error") {
      for {
        client           <- ZIO.service[Client]
        _                <- TestClient.addRoutes {
          routes
        }
        helloResponse    <- client.batched(Request.get(URL.root / "hello" / "world"))
        helloBody        <- helloResponse.body.asString
      } yield assertTrue(helloBody == "Content cannot be viewed via this method")
    }.provide(TestClient.layer),

    test("Request with HX-Request header false should return response") {
      for {
        client           <- ZIO.service[Client]
        _                <- TestClient.addRoutes {
          routes
        }
        helloResponse    <- client.batched(Request.get(URL.root / "hello" / "world").addHeader(Header.Custom("HX-Request", "false")))
        helloBody        <- helloResponse.body.asString
      } yield assertTrue(helloBody == "Hey there!")
    }.provide(TestClient.layer)
  )
}
