package models

import akka.actor.{Actor, ActorLogging, Props}

class HelloActor extends Actor with ActorLogging{
  override def receive: Receive = {
    case SayHello(name) => {
      println(s"Calling ${self.path.name}")
      sender() ! s"Hello ${name}!"
    }
   }
}

object HelloActor {
  def apply() = new HelloActor()

  def props(): Props = Props(HelloActor())
}

case class SayHello(name: String)
