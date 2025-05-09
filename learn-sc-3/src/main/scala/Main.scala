//> using Scala "3"
//> using lib "dev.zio::zio::2.1.6"

import zio.{Console, ZIOAppDefault}
class Person(val name: String, val age: Int)

object Person:
  def unapply(p: Person): Option[(String, Int)] = Option((p.name, p.age))

object Main extends ZIOAppDefault:

  def run = Console.print("Hello world")


