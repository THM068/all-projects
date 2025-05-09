ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.10"

libraryDependencies += "com.twitter" %% "finagle-http" % "22.4.0"
// https://mvnrepository.com/artifact/org.json4s/json4s-jackson
libraryDependencies += "org.json4s" %% "json4s-jackson" % "4.0.6"
libraryDependencies += ("com.faunadb" %% "faunadb-scala" % "4.3.0")


lazy val root = (project in file("."))
  .settings(
    name := "sakila-api"
  )
