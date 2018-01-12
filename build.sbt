name := "sangria-relay"
organization := "org.slackoverflow"
version := "1.3.4"

description := "Sangria Relay Support"
homepage := Some(url("http://sangria-graphql.org"))
licenses := Seq("Apache-2.0" → url("http://www.apache.org/licenses/LICENSE-2.0"))

scalaVersion := "2.12.4"
crossScalaVersions := Seq("2.11.11", "2.12.4")

scalacOptions ++= Seq("-deprecation", "-feature")

scalacOptions ++= {
  if (scalaVersion.value startsWith "2.12")
    Seq.empty
  else
    Seq("-target:jvm-1.7")
}

libraryDependencies ++= Seq(
  "org.sangria-graphql" %% "sangria" % "1.3.3",
  "org.scalatest" %% "scalatest" % "3.0.4" % "test")

// Publishing

publishMavenStyle := true
publishArtifact in Test := false
pomIncludeRepository := (_ ⇒ false)

//publishTo := Some(
//  if (version.value.trim.endsWith("SNAPSHOT"))
//    "snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
//  else
//    "releases" at "https://oss.sonatype.org/service/local/staging/deploy/maven2")

resolvers += "Sonatype snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

startYear := Some(2015)
organizationHomepage := Some(url("https://github.com/sangria-graphql"))
developers := Developer("OlegIlyenko", "Oleg Ilyenko", "", url("https://github.com/OlegIlyenko")) :: Nil
scmInfo := Some(ScmInfo(
  browseUrl = url("https://github.com/sangria-graphql/sangria-relay.git"),
  connection = "scm:git:git@github.com:sangria-graphql/sangria-relay.git"
))

// nice *magenta* prompt!

shellPrompt in ThisBuild := { state ⇒
  scala.Console.MAGENTA + Project.extract(state).currentRef.project + "> " + scala.Console.RESET
}