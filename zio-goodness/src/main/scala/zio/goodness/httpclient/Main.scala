package zio.goodness.httpclient

import zio._

object Main extends ZIOAppDefault{
  val url = "https://icanhazdadjoke.com/"
  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = ???
}
