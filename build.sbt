name := """spark-redis"""

version := "1.0"

scalaVersion := "2.10.6"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "1.6.0" % "provided"
  ,"org.scalatest"   %% "scalatest"  % "2.2.4" % "test"
)
