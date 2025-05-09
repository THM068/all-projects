import zio.{ZIO, ZIOAppDefault, durationInt}

import java.util.concurrent.TimeoutException
import scala.io.Source

object ZioHttpWithTimeout extends ZIOAppDefault {
  val getUrlContent: ZIO[Any, Throwable, String] = {
    val url = "https://httpbin.org/get"
    ZIO.attempt {
      Source.fromURL(url).mkString
    }.timeout(20000.millis).flatMap(convertOptionToZio(_, new TimeoutException("Timeout")))
  }

  def convertOptionToZio[A, E](entry: Option[A], error: => E) =
    ZIO.fromOption(entry)
      .orElseFail(error)
      //.mapError(_ => error)

  val bluePrint = for {
    content <- getUrlContent
  } yield content

  val run: ZIO[Any, Nothing, Unit] = bluePrint.foldZIO(
    failure => ZIO.succeed(println(s"Failed with $failure")),
    result => ZIO.succeed(println(result))
  )

}
