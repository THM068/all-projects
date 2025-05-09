package dependency.injection

import zio.{Console, IO, ZEnvironment, ZIO, ZIOAppDefault, ZLayer}

import java.io.IOException

object Main extends ZIOAppDefault {
  val run = (for {
    _ <- ZIO.serviceWithZIO[Printer](_.print("Hello, World!"))
    _ <- readyToRun
  } yield ())
    .provide(Printer.layer)

  val needsAnInt: ZIO[Int, Nothing, Unit] = ZIO.serviceWithZIO[Int](ZIO.debug(_))

  lazy val readyToRun = needsAnInt.provideEnvironment(ZEnvironment(23))

}

class Printer {
  def print(s: String): IO[IOException, Unit] = Console.printLine(s)
}

object Printer {
  def layer: ZLayer[Any, Nothing, Printer] = ZLayer.succeed(new Printer)
}
