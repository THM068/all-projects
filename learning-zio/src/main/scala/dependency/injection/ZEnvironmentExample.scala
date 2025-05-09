package dependency.injection

import zio.{Scope, ZIO, ZIOAppArgs, ZIOAppDefault, Console, ZEnvironment}

object ZEnvironmentExample extends ZIOAppDefault {
  trait Foo {
    def bar: Int
  }
  val run  = (for {
    s <- ZIO.service[String]
    _<- Console.printLine(s"The string value is $s")
    foo <- ZIO.service[Foo]
    _ <- Console.printLine(s"The value of foo is ${foo.bar}")
  } yield ())
    .provideEnvironment(ZEnvironment.empty
      .add("Hello ZIO!")
      .add(new Foo {
        def bar = 42
      })
    )

}
