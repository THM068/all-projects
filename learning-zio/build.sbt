ThisBuild / scalaVersion     := "2.12.18"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "learning-zio",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % "2.1.1",
      "dev.zio" %% "zio-streams" % "2.1.13",
      "dev.zio" %% "zio-test" % "2.1.1" % Test
    ),
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  )


