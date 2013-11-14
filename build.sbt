sbtPlugin := true

organization := "su.e-terra"

name := "sbt-liftl10n-plugin"

description := "SBT plugin to check Lift based projects localization"

version := "0.0.2"

scalaVersion := "2.10.3"

licenses := Seq("BSD License" -> url("https://raw.github.com/limansky/sbt-liftl10n-plugin/master/LICENSE"))

homepage := Some(url("https://github.com/limansky/sbt-liftl10n-plugin"))

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
  <url>https://github.com/limansky/sbt-liftl10n-plugin</url>
  <scm>
    <url>git@github.com:limansky/sbt-liftl10n-plugin.git</url>
    <connection>scm:git:git@github.com:limansky/sbt-liftl10n-plugin.git</connection>
  </scm>
  <developers>
    <developer>
      <id>limansky</id>
      <name>Mike Limansky</name>
      <url>http://github.com/limansky</url>
    </developer>
  </developers>)
