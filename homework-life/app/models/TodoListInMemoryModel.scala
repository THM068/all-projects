package models

import scala.collection.mutable

object TodoListInMemoryModel {
  private val users = mutable.Map[String,String]("jim" -> "brown")

  def validateUser(usernname: String, password: String): Boolean = {
    users.get(usernname).map( _ == password).getOrElse(false)
  }
}
