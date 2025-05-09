import cats.effect.{ExitCode, IO, IOApp}

object Main extends IOApp.Simple {

  import debug._
  val hello = IO("hello ")
  val world = IO("world")

  val par = (hello,world).parMapN((a,b) => a + b).flatMap { result =>
    IO.println(s"Result: $result")
  }


  override def run: IO[Unit] = par.as(ExitCode.Success)
}

object debug {
  implicit class DebugHelper[A](ioa: IO[A]) {
    def debug: IO[A] =
      for {
        result <- ioa
        tn = Thread.currentThread().getName
        _ <- IO.println(s"Thread: $tn")
      } yield result
  }
}