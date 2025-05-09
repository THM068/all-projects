import scala.collection.Seq

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.20"

lazy val root = (project in file("."))
  .settings(
    name := "learn-scala-effects",
      libraryDependencies ++= Seq( "org.typelevel" %% "cats-effect" % "3.6.1")

)
