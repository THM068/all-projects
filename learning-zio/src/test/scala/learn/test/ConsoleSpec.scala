package learn.test

import learn.test.HelloWorld.sayHello
import zio.{Console, Scope, ZIO, durationInt}
import zio.test.Assertion.{assertion, containsString, equalTo, hasSize}
import zio.test.{Spec, TestClock, TestConsole, TestEnvironment, ZIOSpecDefault, assertCompletes, assertTrue}

import java.io.IOException

object ConsolesSpec extends ZIOSpecDefault {
  val goShopping: ZIO[Any, IOException, Unit] =
    Console.printLine("Go shopping").orDie.delay(1.hour)
  override def spec: Spec[TestEnvironment with Scope, Any] = zio.test.suite("Console testing")(
    test("sayHello correctly displays output") {
      for {
        _      <- sayHello
        output <- TestConsole.output
      } yield assertTrue(output == Vector("Hello, World!\n"))
    },
    test(""){
      for {
        _ <- TestConsole.feedLines("Hello, World!")
        output <- TestConsole.output
      } yield {
        assertTrue(output == Vector("Hello, World!\n"))      }
    },
    test("Go shopping") {
      for {
        goShop <- goShopping.fork
        _ <- TestClock.adjust(1.hour)
        _ <- goShop.join
        output <- TestConsole.output
      } yield {
        assertCompletes
        assertTrue(output == Vector("Go shopping\n"))
      }
    }
  )
}

object HelloWorld {
  def sayHello: ZIO[Any, IOException, Unit] =
    Console.printLine("Hello, World!")
}
