package tref

import zio.{ZIO, ZIOAppDefault, durationInt}
import zio.stm.TRef

object SimpleCounter extends ZIOAppDefault {

  val run  = for {
    counter <- TRef.make(0).commit
    _ <- ZIO.foreachPar(1 to 1000) { _ =>
      counter.updateAndGet(_ + 1).commit
    }.fork
    _ <- ZIO.foreachPar(1 to 1000) { _ =>
      counter.updateAndGet(_ - 1).commit
    }.fork
    _ <- ZIO.sleep(3.second)
    finalValue <- counter.get.commit
    _ <- zio.Console.printLine(s"Final value: $finalValue")
  } yield ()

}
