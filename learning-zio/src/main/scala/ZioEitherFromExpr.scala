import zio.{ZIO, ZIOAppDefault}

import scala.util.control.Exception.allCatch

object ZioEitherFromExpr extends ZIOAppDefault {

  def makeInt(entry: String): Either[Throwable, Int]= allCatch.either(entry.toInt)

  val bluePrint = for {
    a <- ZIO.fromEither(makeInt("1"))
    b <- ZIO.succeed(2)
    c <- ZIO.fromOption(Some(3))
  } yield a + b + c

  val run = bluePrint.foldZIO(
    failure => ZIO.succeed(println(s"Failed with $failure")),
    success => ZIO.succeed(println(s"Result is $success"))
  )

}
