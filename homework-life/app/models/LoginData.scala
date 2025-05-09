package models

case class LoginData(username: String, password: String)

case class RegisterData(name: String, username: String, email: String, password: String)