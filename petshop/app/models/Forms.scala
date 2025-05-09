package models


import play.api.data.Form
import play.api.data.Forms.{mapping, nonEmptyText, number}

final case class Student(name: String, surname: String, age: Int)
object Forms {

  val studentForm = Form(
    mapping(
      "name" -> nonEmptyText,
      "surname" -> nonEmptyText,
      "age" -> number(min = 0, max = 100)
    )(Student.apply)(Student.unapply)
  )
}
