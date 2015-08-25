name := "meta-scala-beer"

version := "1.0"

scalaVersion := "2.11.7"

def withTests = libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.3" % "test"
)


lazy val pub = project settings {
  withTests
}

lazy val macropub = project dependsOn pub settings (
  libraryDependencies <+= scalaVersion("org.scala-lang" % "scala-compiler" % _)
  )

lazy val pluginPub = project dependsOn pub