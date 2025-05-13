import zio.Console.ConsoleLive.printLine
import zio.{Console, ZIO, ZIOAppDefault}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.io.StdIn
import scala.concurrent.{ExecutionContext, Future}

object SequentialApp extends ZIOAppDefault {
  val prints =
     List(
       printLine("hello"),
       printLine("quick"),
       printLine("brown"),
       ZIO.fail("fox")
   )

   val printWords = ZIO.collectAllPar(prints)

  val firstName = ZIO.attempt(StdIn.readLine("What is your first name? "))
  val lastName = ZIO.attempt(StdIn.readLine("What is your last name? "))

  val fullName = firstName.zipWith(lastName) { (first, last) =>
    s"$first $last"
  }
  val goShoppingFuture: Future[Unit] =  Future {
    println("Go shopping")
  }
  val run = for {
    _ <- ZIO.debug("Hello world")
    re <- printWords.exit
    _ <- ZIO.debug(s"Result: $re")
    _ <- fullName.flatMap(name => printLine(s"Hello $name"))
    _ <- ZIO.fromFuture(_ => goShoppingFuture)
  } yield ()



}
