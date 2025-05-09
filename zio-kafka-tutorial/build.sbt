ThisBuild / scalaVersion     := "2.13.10"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.kafka.app"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "zio-kafka-tutorial",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % "2.0.13",
      "dev.zio" %% "zio-test" % "2.0.13" % Test,
      "dev.zio" %% "zio-streams" % "2.0.9",
      "dev.zio" %% "zio-kafka" % "2.1.1",
      "dev.zio" %% "zio-json" % "0.5.0"
    ),
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  )
