package fibres

import zio.{Schedule, ZIO, ZIOAppDefault, durationInt}

object InterruptFiberExample extends ZIOAppDefault {
  lazy val doSomething: ZIO[Any, Nothing, Long] = ZIO.debug("some long running task!").repeat(Schedule.spaced(2.seconds))

  val run = for {
    _ <- ZIO.debug("Starting the program!")
    fiber <- doSomething.fork
    _<- ZIO.sleep(10.seconds)
    _ <- fiber.interrupt
    _ <- ZIO.debug("The program has ended!")
  }yield()

}
