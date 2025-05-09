import zio.{Chunk, Console, ZIO, ZIOAppArgs, ZIOAppDefault}

import java._
import java.io.IOException

object ZioArgTest extends ZIOAppDefault {

  val failWithMessageEffect: ZIO[Any, Throwable, Unit] = Console.printLineError("No arguments provided") *> ZIO.fail(new Exception("No arguments provided"))

  val bluePrint: ZIO[ZIOAppArgs, Throwable, Chunk[String]] = for {
    args <- ZIOAppArgs.getArgs
    _ <- ZIO.when(args.isEmpty)(failWithMessageEffect)
  } yield args

  val run: ZIO[ZIOAppArgs,IOException,Unit] = bluePrint.foldZIO(
    failure => Console.printLineError(s"Failed with $failure"),
    args => ZIO.foreach(args){ arg =>
      Console.printLine(arg)
    }.unit
  )

}
