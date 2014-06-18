sbtPlugin := true

organization := "su.e-terra"

name := "sbt-liftl10n-plugin"

description := "SBT plugin to check Lift based projects localization"

version := "0.0.3-SNAPSHOT"

scalaVersion := "2.10.4"

licenses := Seq("BSD License" -> url("https://raw.github.com/limansky/sbt-liftl10n-plugin/master/LICENSE"))

homepage := Some(url("https://github.com/limansky/sbt-liftl10n-plugin"))

scmInfo := Some(
  ScmInfo(
    url("https://github.com/limansky/sbt-liftl10n-plugin.git"),
    "scm:git:https://github.com/limansky/sbt-liftl10n-plugin.git",
    Some("scm:git:git@github.com:limansky/sbt-liftl10n-plugin.git")
  )
)

publishMavenStyle := true

publishArtifact in Test := false

publishTo := {
  val nexus = "http://oss.sonatype.org/"
  if (version.value.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

pomExtra := (
  <developers>
    <developer>
      <id>limansky</id>
      <name>Mike Limansky</name>
      <url>http://github.com/limansky</url>
    </developer>
  </developers>)
