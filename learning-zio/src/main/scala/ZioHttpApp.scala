import zio.{ZIO, ZIOAppDefault, durationInt}

import scala.io.Source

object ZioHttpApp extends ZIOAppDefault {

  val bluePrint: ZIO[Any, Throwable, String] = {
    val url = "https://httpbin.org/get"
    ZIO.attempt {
      Source.fromURL(url).mkString
    }.timeout(20000.millis).flatMap(convertOption(_, new Exception("Timeout")))
  }

  val run = bluePrint.foldZIO(
    failure => ZIO.succeed(println(s"Failed with $failure")),
    result => ZIO.succeed(println(result))
  )

  def convertOption[A,E](entry: Option[A], error: => E) =
    ZIO.fromOption(entry)
      .orElseFail(error)
}
