name := """petshop"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.16"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.0" % Test
libraryDependencies += "org.typelevel" %% "cats-core" % "2.13.0"
// https://mvnrepository.com/artifact/uk.gov.hmrc/play-frontend-hmrc-play-30
// https://mvnrepository.com/artifact/uk.gov.hmrc/play-frontend-hmrc-play-30
libraryDependencies += "org.reactivemongo" %% "play2-reactivemongo" % "1.1.0-play30.RC15"
libraryDependencies +=  "org.reactivemongo" %% "reactivemongo-play-json-compat" % "1.1.0-play30.RC15"
// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
