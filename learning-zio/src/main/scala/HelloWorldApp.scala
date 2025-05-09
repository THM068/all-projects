import zio.{Console, Scope, Task, ZIO, ZIOAppArgs, ZIOAppDefault}

object HelloWorldApp extends ZIOAppDefault{

  val printMessage: ZIO[Any, Throwable, Unit] = for {
    name <- makeInt("1234")
    _ <- Console.printLine(s"Hello, $name!")
  } yield ()

  def run = printMessage

  def makeInt(s: String):ZIO[Any, NumberFormatException, Int] =
    ZIO.attempt(s.toInt)
      .refineToOrDie[NumberFormatException]


}
