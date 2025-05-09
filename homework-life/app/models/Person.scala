package models

import play.api.libs.json.{Json, Writes, Reads}

case class Person (name: String="Thando", lastName: String = "Mafela")

object Person {
  implicit val personWrites: Writes[Person] = Json.writes[Person]
  implicit val personRead: Reads[Person] = Json.reads[Person]
}
