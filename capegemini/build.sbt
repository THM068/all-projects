ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.1"

lazy val root = (project in file("."))
  .settings(
    name := "capegemini"
  )

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.12" % "test"

libraryDependencies += "org.scalamock" %% "scalamock" % "5.1.0" % Test

libraryDependencies ++= Seq(
  "org.creativescala" %% "doodle" % "0.9.6",
  "org.creativescala" %% "doodle-reactor" % "0.9.6"
)
