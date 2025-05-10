import zio.{Console, Unsafe, ZIO, ZIOApp, ZIOAppArgs, ZIOAppDefault, ZLayer}

object ZioAppsInParallel extends ZIOApp.Proxy(MyApp1 <> MyApp2) {

}

object MyApp1 extends ZIOAppDefault {
  override val bootstrap = zio.Runtime.enableLoomBasedExecutor
  val run = ZIO.attempt {
    println(s"Task running on a virtual-thread: ${Thread.currentThread()}")
  }
}

object MyApp2 extends ZIOAppDefault {
  val run = Console.printLine("Hello from MyApp2")*> ZIO.fail("failed")
}

object ZIO_App_Proxy extends App {
  val bluePrint = for {
    name<- Console.readLine("Enter your name: ")
    _ <- Console.printLine("Hello, " + name)
    _ <- ZIO.log("Hello, " + name)
  } yield name

  val x = Unsafe.unsafe { implicit unsafe =>
    zio.Runtime.default.unsafe.run(
      ZIO.succeed("hello world")
    ).getOrThrowFiberFailure()
  }
  println(x)
  def runEffect(effect: => ZIO[Any, Throwable, String]): String = {
    val runtime= zio.Runtime.default
    val result = Unsafe.unsafe { implicit unsafe =>
      runtime.unsafe.run(effect).getOrThrowFiberFailure()
    }
    result
  }

  println(runEffect(ZIO.succeed("hello world")))

  runEffect(bluePrint)

  Unsafe.unsafe {implicit  unsafe =>
    zio.Runtime.default.unsafe.run(bluePrint).getOrThrowFiberFailure()
  }
}
