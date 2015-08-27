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


lazy val pluginPub = project settings (globalSettings: _*)

val location = "pluginPub/target/scala-2.11/"

val pluginLocation = (file("pluginPub") / "target" / "scala-2.11" ** "*.jar").get.head.getAbsolutePath

def withPubPlugin = Seq(
  unmanagedJars +=
  scalacOptions += "-Xplugin"
)


lazy val pub = project dependsOn pluginPub settings (globalSettings: _*) settings (withPubPlugin: _*)

lazy val macropub = project dependsOn pub settings (globalSettings: _*)
