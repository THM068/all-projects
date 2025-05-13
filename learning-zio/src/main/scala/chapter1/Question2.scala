package chapter1

import zio.{Task, ZIO, ZIOAppDefault, Console}

import java.io.{FileWriter, PrintWriter}

object Question2 extends ZIOAppDefault {
  val resource = "/Users/tma24/private_projects/learning-zio/src/main/resources/testdata/writeTo.txt"

  def acquireWriter(f: String): Task[PrintWriter] = ZIO.attempt {
    val fileWriter = new FileWriter(f)
    new PrintWriter(fileWriter)
  }

  def releaseWriter(writer: PrintWriter) = ZIO.succeed(writer.close())

  def writeToFile(writer: PrintWriter): Task[Unit] = for {
    content <- Console.readLine("Enter content to write to file: ")
    _ <- ZIO.attempt {
      writer.write(content)
      writer.flush()}
  }yield ()


  val run =
    ZIO.acquireReleaseWith(acquireWriter(resource))(releaseWriter)(writeToFile).foldZIO(
    failure => ZIO.succeed(println(s"Failed with $failure")),
    _ => ZIO.succeed(println("Write to file completed successfully"))
  )

}
