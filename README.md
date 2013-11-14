sbt-liftl10n-plugin
===================

  sbt-liftl10n-plugin is a plugin for [Simple Build Tool](http://www.scala-sbt.org), which allows
to check localization in the [Lift](http://www.liftweb.net) projects.  The plugin scans Scala
sources and html files for localization IDs, and check if the id is available in all translation 
files.

  This plugin is compatible with sbt 0.13 or later, however it's should not be a big problem to
port it to the older sbt if it really needed.

  Currently the plugin is in early development stage, so the names of settings and other parameters
can be changed.

Usage
-----

To use the plugin and following lines in the `project/plugins.scala`

```
addSbtPlugin("su.e-terra" % "sbt-liftl10n-plugin" % "0.0.2")
```

To check the code use `l10ncheck` command in sbt console.

To check the localization IDs format use `locIdFormat` parameter, which takes `Option[Regex]` to define
correct pattern for IDs you using. For example if you are using `domain.name[.second]` format, where all
tokens are in camel case add following line in your build.sbt:

```
locIdFormat := Some("""^[a-z][A-Za-z]*(\.[a-z][A-Za-z]*)+$""".r)
```

License
-------

sbt-liftl10n-plugin is distributed in terms of BSD license.
