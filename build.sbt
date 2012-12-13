sbtPlugin := true

organization := "su.eterra"

name := "sbt-liftl10n-plugin"

version := "0.0.2"

scalaVersion := "2.9.2"

crossScalaVersions := (Seq("2.9.2", "2.9.1"))

licenses += ("BSD License", url("https://raw.github.com/limansky/sbt-liftl10n-plugin/master/LICENSE"))

publishTo := Some(Resolver.file("file", Path.userHome / "ghsite" / "maven2"))

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
    </developer>
  </developers>)
