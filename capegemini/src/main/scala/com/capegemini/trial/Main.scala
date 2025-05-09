package com.capegemini.trial
import scala.util.matching.Regex

object Main extends App {
  test("+447774003798")

  object isUkNumber {
    private val ukNumberPattern: Regex = "^\\+447[0-9]{9}$".r

    def unapply(input: String): Boolean = {
      ukNumberPattern.findFirstMatchIn(input) match {
        case Some(_) => true
        case None => false
      }
    }
  }

  def test(name: String): Unit = {
    name match {
      case isUkNumber() => println("I am uk number")
      case _ => println("I am not a uk number")
    }
  }



}
