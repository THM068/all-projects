ThisBuild / scalaVersion     := "3.0.1"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "learn.sc3.zio"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "lean-s3-zio",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % "2.0.17",
      "dev.zio" %% "zio-test" % "2.0.17" % Test
    ),
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  )
