package core

import zio.{Clock, Console, ZIO, ZIOAppDefault, durationInt}

import java.io.IOException
import java.util.concurrent.TimeUnit

object Main extends ZIOAppDefault{

  val readInt: ZIO[Any, Throwable, Int] =
     for {
      line <- Console.readLine
      int <- ZIO.attempt(line.toInt)
     } yield int

  lazy val readIntOrRetry: ZIO[Any, IOException, Int] = readInt
   .orElse(Console.printLine("Please enter a valid integer")
   .zipRight(readIntOrRetry)
   )

  val clock = Clock.currentTime(TimeUnit.MILLISECONDS)
  val run = readIntOrRetry


}
