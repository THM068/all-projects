import zio.Console.ConsoleLive.printLine
import zio._

import java.io.IOException
import scala.io.StdIn

object Main extends ZIOAppDefault {

  val makeIntDouble = makeInt("2").map(_*2)
  val makeIntSquare = makeIntDouble.map(r => r*r)

  val app: ZIO[Any, NumberFormatException, Int] = for {
    a <- makeInt("1")
    b <- makeInt("2")
    c <- makeInt("3")
  } yield a + b + c

  override val run: ZIO[Any, IOException, Unit] = app.foldZIO(
    failure => Console.printError(s"Failed with $failure"),
    success => Console.printLine(s"Result is $success")
  )



  def makeInt(entry: String): ZIO[Any, NumberFormatException, Int] = ZIO.attempt(entry.toInt).refineToOrDie[NumberFormatException]

  def hello: ZIO[Any, Throwable, Unit] = Console.printLine("Hello, World!")

  val promptForName:ZIO[Any, Throwable, String] = ZIO.attempt(StdIn.readLine("What is your name?"))

}

