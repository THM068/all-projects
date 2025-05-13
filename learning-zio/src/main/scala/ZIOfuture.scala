import zio._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
object ZIOfuture extends ZIOAppDefault{
  def future() = Future {
    println("Hello from Future!")
  }

  val runFuture = ZIO.fromFuture { implicit ec =>
    future()
  }
  val run = Console.printLine("Hello") *>
    runFuture 
}
