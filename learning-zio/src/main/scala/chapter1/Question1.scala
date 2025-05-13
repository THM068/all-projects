package chapter1

import zio.{Console, Task, ZIO, ZIOAppDefault}

import java.io.{BufferedReader, FileReader}
import java.util
import java.util.stream.Collectors

object Question1 extends ZIOAppDefault {
  val resource = "/Users/tma24/private_projects/learning-zio/src/main/resources/testdata/fahrenheit.txt"

  val run = readFile(resource).foldZIO(
    failure => ZIO.succeed(println(s"Failed with $failure")),
    result => Console.printLine(result)
  )

  def readFile(file: String): ZIO[Any, Throwable, String] =
    ZIO.attempt {
      val source = scala.io.Source.fromFile(file)
      try source.getLines().mkString("\n")
      catch {
        case e: Exception => s"Error reading file: ${e.getMessage}"
      }
      finally source.close()
    }
}

object Question1AcquireRelease extends ZIOAppDefault {
  val resource = "/Users/tma24/private_projects/learning-zio/src/main/resources/testdata/fahrenheit.txt"

  def acquireReader(f: String):Task[BufferedReader] = ZIO.attempt {
    new BufferedReader(new FileReader(resource), 2048)
  }

  def releaseReader(reader: BufferedReader) = ZIO.succeed {
    reader.close()
  }
  def countLines(reader: BufferedReader): Task[util.List[String]]    = ZIO.attempt(reader.lines().collect(Collectors.toList[String]()))

  def content(reader: BufferedReader): Task[util.List[String]] = ZIO.attempt(
    reader.lines()
      .collect(Collectors.toList[String]()))



  val run = ZIO.acquireReleaseWith(acquireReader(resource))(releaseReader)(content).foldZIO(
    failure => ZIO.succeed(println(s"Failed with $failure")),
    result => ZIO.succeed(result.forEach { line =>
      println(s"Line: $line")

    }
  ))
}
