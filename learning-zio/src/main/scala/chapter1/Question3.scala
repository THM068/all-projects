package chapter1

import zio.{Task, ZIO, ZIOAppDefault}

import java.io.{BufferedReader, FileReader, FileWriter, PrintWriter}
import java.util
import java.util.stream.Collectors
import scala.collection.convert.ImplicitConversions.`collection AsScalaIterable`

object Question3 extends ZIOAppDefault {

  val resource = "/Users/tma24/private_projects/learning-zio/src/main/resources/testdata/fahrenheit.txt"
  val resourceWriter = "/Users/tma24/private_projects/learning-zio/src/main/resources/testdata/writeTo.txt"

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

  def acquireWriter(f: String): Task[PrintWriter] = ZIO.attempt {
    val fileWriter = new FileWriter(f)
    new PrintWriter(fileWriter)
  }

  def releaseWriter(writer: PrintWriter) = ZIO.succeed(writer.close())

  val run = for {
    lines <- ZIO.acquireReleaseWith(acquireReader(resource))(releaseReader)(content)

    _ <- ZIO.acquireReleaseWith(acquireWriter(resourceWriter))(releaseWriter) { writer =>
      ZIO.succeed(writer.write(lines.mkString("\n")))
    }

  }yield ()

}
