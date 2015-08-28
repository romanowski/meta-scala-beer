name := "meta-scala-beer"

version := "1.0"

val scalaVersionString = "2.11.7"




def globalSettings = Seq(
  scalaVersion := scalaVersionString,
  scalacOptions ++= Seq(
    "-deprecation",
    "-unchecked",
    "-feature",
    "-Xlint",
    "-Xfatal-warnings"
  ),
  libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.3" % "test",
  libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersionString,
  libraryDependencies += "org.scala-lang" % "scala-compiler" % scalaVersionString
)

addCommandAlias("testCompilerPlugin", ";clean;pluginPub/package;test")
addCommandAlias("runCompilerPlugin", ";clean;pluginPub/package;compile")
addCommandAlias("initCompilerPlugin", ";clean;pluginPub/package;reload")
addCommandAlias("testPub", ";macropub/clean;macropub/test")

addCommandAlias("tp", "testPub")
addCommandAlias("icp", "initCompilerPlugin")
addCommandAlias("tcp", "testCompilerPlugin")
addCommandAlias("rcp", "runCompilerPlugin")

lazy val pluginPub = project settings (globalSettings: _*)

val location = "pluginPub/target/scala-2.11/"

def pluginLocation = (file("pluginPub") / "target" / "scala-2.11" ** "*.jar").get.headOption.map(_.getAbsolutePath).orElse{
  println("No plugin")
  None
}

def withPubPlugin = pluginLocation.toList.map(path => scalacOptions += s"-Xplugin:$path")


lazy val pub = project dependsOn pluginPub settings (globalSettings: _*) settings (withPubPlugin: _*)

lazy val macropub = project dependsOn pub settings (globalSettings: _*)
