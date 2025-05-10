import zio.Console.ConsoleLive.printLine
import zio.{ZIO, ZIOAppDefault}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.io.StdIn
import scala.concurrent.{ExecutionContext, Future}

object SequentialApp extends ZIOAppDefault {
  val prints =
     List(
       printLine("The"),
       printLine("quick"),
       printLine("brown"),
       printLine("fox")
   )

   val printWords = ZIO.collectAll(prints)

  val firstName = ZIO.attempt(StdIn.readLine("What is your first name? "))
  val lastName = ZIO.attempt(StdIn.readLine("What is your last name? "))

  val fullName = firstName.zipWith(lastName) { (first, last) =>
    s"$first $last"
  }
  val goShoppingFuture: Future[Unit] =  Future {
    println("Go shopping")
  }
  val run =  printWords



}
