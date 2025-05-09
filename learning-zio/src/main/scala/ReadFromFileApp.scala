import zio.{Task, UIO, ZIO, ZIOAppDefault}

import java._
import java.io.{BufferedReader, FileReader}
import java.util.stream.Collectors

object ReadFromFileApp extends ZIOAppDefault {
  val resource = "/Users/tma24/private_projects/learning-zio/src/main/resources/testdata/fahrenheit.txt"

  def fahrenheitToCelsius(f: Double): Double =
    (f - 32.0) * (5.0/9.0)

  def acquireReader(resource: String):Task[BufferedReader] = ZIO.attempt {
    new BufferedReader(new FileReader(resource), 2048)
  }

  def releaseReader(reader: BufferedReader): UIO[Unit]  = ZIO.succeed(reader.close())

  def countLines(reader: BufferedReader): Task[util.List[String]]    = ZIO.attempt(reader.lines().collect(Collectors.toList[String]()))

  val run: ZIO[Any, Nothing, Unit] = ZIO.acquireReleaseWith(acquireReader(resource))(releaseReader)(countLines).foldZIO(
    failure => ZIO.succeed(println(s"Failed with $failure")),
    result => ZIO.succeed(result.forEach{ line =>
      val fahrenheit = line.toDouble
      val celsius = fahrenheitToCelsius(fahrenheit)
      println(s"$fahrenheit F = $celsius C")

    })
  )

}
