addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "0.5.1")
addSbtPlugin("com.jsuereth" % "sbt-pgp" % "1.0.0")

logLevel := Level.Warn

resolvers += Classpaths.sbtPluginReleases

addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.0.4")

addSbtPlugin("org.scoverage" % "sbt-coveralls" % "1.0.0")