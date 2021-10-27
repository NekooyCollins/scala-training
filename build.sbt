name := "scala-training"

version := "0.1"

scalaVersion := "2.13.6"


val akkaHttpVersion = "10.1.11"
val akkaVersion = "2.6.5"

// Akka
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "org.scala-lang" % "scala-reflect" % scalaVersion.value
)