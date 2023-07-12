ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.0"

lazy val root = (project in file("."))
  .settings(
    name := "FlatNotifier",
    idePackagePrefix := Some("me.zdziszkee.flatnotifier")
  )
// https://mvnrepository.com/artifact/com.typesafe.akka/akka-http
libraryDependencies += "com.typesafe.akka" %% "akka-http" % "10.5.2"
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.8.3"
libraryDependencies += "com.typesafe.akka" %% "akka-http-spray-json" % "10.5.2"
libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.8.3"
libraryDependencies += "io.spray" %%  "spray-json" % "1.3.6"

// https://mvnrepository.com/artifact/org.jsoup/jsoup
libraryDependencies += "org.jsoup" % "jsoup" % "1.16.1"
// https://mvnrepository.com/artifact/io.circe/circe-core
val circeVersion = "0.14.1"

