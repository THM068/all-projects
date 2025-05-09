import com.typesafe.sbt.packager.docker._
dockerChmodType := DockerChmodType.UserGroupWriteExecute
dockerPermissionStrategy := DockerPermissionStrategy.CopyChown

ThisBuild / scalaVersion := "2.12.11"

ThisBuild / version := "1.0-SNAPSHOT"

PlayKeys.devSettings += "play.server.http.port" -> "8082"
PlayKeys.playDefaultPort  := 8080


lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := """diary-app""",
    libraryDependencies ++= Seq(
      guice,
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test,
      "com.faunadb" %% "faunadb-scala" % "4.4.0",
      "com.typesafe.play" %% "play-slick" % "5.0.0",
      "org.postgresql" % "postgresql" % "42.6.0",
      "org.mockito" % "mockito-core" % "5.4.0" % Test,
      "com.typesafe.slick" %% "slick-hikaricp" % "3.3.2"
    )
  )
// https://mvnrepository.com/artifact/org.mockito/mockito-core

libraryDependencies += filters
