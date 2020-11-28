libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-effect" % "2.2.0",
  "org.scalatest" %% "scalatest" % "3.2.2" % "test"
)

// scalaVersion := {
//   val version = (math.random() * 5).toInt
//   s"2.13.$version"

//   scalaVersion.value ++ "mine!"
// }

run :=  println(s"Hello World: ${scalaVersion.value}")

Compile / scalaVersion := "2.12.2"

scalaVersion := "2.13.4"