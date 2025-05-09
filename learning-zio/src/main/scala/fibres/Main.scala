package fibres

import zio.{ZIO, ZIOAppDefault, durationInt}

object Main extends ZIOAppDefault {
  lazy val doSomething: ZIO[Any, String, Nothing] =
     ZIO.debug("do something!").delay(2.seconds) *> ZIO.fail("boom! failure!")

  def run = for {
    f <- doSomething.fork
    exit <- f.await
    _<- exit.foldZIO(
      err => ZIO.debug(err),
      _ => ZIO.debug("done")
    )
    _ <- ZIO.debug("I'm done!")
  } yield ()

}
