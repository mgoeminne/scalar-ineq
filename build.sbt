name := "ineq"

version := "1.0"

scalaVersion := "2.11.7"

publishMavenStyle := true

publishTo := {
   val nexus = "https://oss.sonatype.org/"
   if (isSnapshot.value)
      Some("snapshots" at nexus + "content/repositories/snapshots")
   else
      Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

pomExtra := (
   <url>https://github.com/mgoeminne/scalar-ineq</url>
      <licenses>
         <license>
            <name>Apache-style</name>
            <url>http://www.apache.org/licenses/LICENSE-2.0</url>
            <distribution>repo</distribution>
         </license>
      </licenses>
      <scm>
         <url>https://github.com/mgoeminne/scalar-ineq.git</url>
         <connection>scm:git:git@github.com:mgoeminne/scalar-ineq.git</connection>
      </scm>
      <developers>
         <developer>
            <id>mgoeminne</id>
            <name>Mathieu Goeminne</name>
            <url></url>
         </developer>
      </developers>)

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.2" % "test"