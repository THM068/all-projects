import sbt.Keys.libraryDependencies

import scala.Seq

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.3"

lazy val root = (project in file("."))
  .settings(
    name := "learn-sc-3",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % "2.1.6"
    )


  )
