ThisBuild / scalaVersion     := "2.12.18"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "zio-goodness",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % "2.0.19",
      "dev.zio" %% "zio-test" % "2.0.19" % Test
    ),
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  )
