ThisBuild / scalaVersion     := "2.12.18"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

ThisBuild / resolvers +=
  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

run / fork := true

lazy val root = (project in file("."))
  .enablePlugins(SbtTwirl)
  .settings(
    name := "vrm-app",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % "2.0.19",
      "dev.zio" %% "zio-json" % "0.7.42",
      "dev.zio" %% "zio-http" % "3.2.0",
//      "dev.zio" %% "zio-http" % "3.0.0-RC9+32-5294e91a-SNAPSHOT",
      "dev.zio" %% "zio-config" % "4.0.0-RC16",
      "dev.zio" %% "zio-config-typesafe" % "4.0.0-RC16",
      "dev.zio" %% "zio-config-magnolia" % "4.0.0-RC16",
      "dev.zio" %% "zio-logging-slf4j2" % "2.1.12",
      "dev.zio" %% "zio-logging" % "2.1.12",
      "org.slf4j" % "slf4j-simple" % "2.0.9",
      "com.pauldijou" %% "jwt-json4s-jackson" % "5.0.0",
      "dev.zio" %% "zio-streams" % "2.0.9",
      "dev.zio" %% "zio-crypto" % "0.0.0+120-8d0af0b1-SNAPSHOT",
      "dev.zio" %% "zio-prelude" % "1.0.0-RC35",
      "dev.zio" %% "zio-test" % "2.0.19" % Test,
      "dev.zio" %% "zio-test-sbt"     % "2.1.9"  % Test,
      "dev.zio" %% "zio-http-testkit" % "3.0.1" % Test
    ),
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  )
