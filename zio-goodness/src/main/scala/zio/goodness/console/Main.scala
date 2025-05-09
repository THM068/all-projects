package zio.goodness.console

import zio._

object  Main extends ZIOAppDefault{

  override def run = for {
    name <- Console.readLine("Hello, please enter your name")
    - <- Console.printLine(s"My name is ${name}")
  } yield Unit
}
