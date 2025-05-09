import zio.{Console, Ref, ZIO, ZIOAppArgs, ZIOAppDefault}

object RefExample001 extends ZIOAppDefault {

  def printRef(i: Int) = Console.printLine(s"Value of ref is: ${i}" )

  def equation = for {
    ref <- Ref.make(1)
    a <- ref.get
    _ <- printRef(a)

    _ <- ref.set(2)
    b <- ref.get
    _ <- printRef(b)

    _ <- ref.update(_ + 2)
    c <- ref.get
    _ <- printRef(c)

    d <- ref.modify(i => (i + 100, i +10))
    e <- ref.get
    _ <- printRef(d)
    _ <- printRef(e)

  } yield ()

  val run = equation

}
