name := "tmm-scala-collections"

ThisBuild / tlBaseVersion := "0.0"

Sonatype.SonatypeKeys.sonatypeProfileName := "au.id.tmm"
ThisBuild / organization := "au.id.tmm.tmm-scala-collections"
ThisBuild / organizationName := "Timothy McCarthy"
ThisBuild / startYear := Some(2020)
ThisBuild / licenses := Seq(License.Apache2)
ThisBuild / developers := List(
  tlGitHubDev("tmccarthy", "Timothy McCarthy"),
)

val Scala213 = "2.13.8"
ThisBuild / scalaVersion := Scala213
ThisBuild / crossScalaVersions := Seq(
  Scala213,
  "3.1.1",
)

ThisBuild / githubWorkflowJavaVersions := List(
  JavaSpec.temurin("11"),
  JavaSpec.temurin("8"),
  JavaSpec.temurin("17"),
)

ThisBuild / tlCiHeaderCheck := false
ThisBuild / tlCiScalafmtCheck := true
ThisBuild / tlCiMimaBinaryIssueCheck := false
ThisBuild / tlFatalWarnings := true

addCommandAlias("check", ";githubWorkflowCheck;scalafmtSbtCheck;+scalafmtCheckAll;+test")
addCommandAlias("fix", ";githubWorkflowGenerate;+scalafmtSbt;+scalafmtAll")

lazy val root = tlCrossRootProject
  .aggregate(
    core,
    scalaCheck,
    cats,
    circe,
  )

val catsVersion      = "2.2.0-M2"
val circeVersion     = "0.14.0-M1"
val scalatestVersion = "3.2.0-M4"
val tmmUtilsVersion  = "0.4.7"

lazy val core = project
  .in(file("core"))
  .settings(name := "tmm-scala-collections-core")
  .settings(
    libraryDependencies += "org.scalatest"       %% "scalatest"         % scalatestVersion % Test,
    libraryDependencies += "au.id.tmm.tmm-utils" %% "tmm-utils-testing" % tmmUtilsVersion  % Test,
  )

lazy val scalaCheck = project
  .in(file("scalacheck"))
  .settings(name := "tmm-scala-collections-scalacheck")
  .settings(
    libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.14.3",
  )
  .settings(
    libraryDependencies += "org.scalatest"       %% "scalatest"         % scalatestVersion % Test,
    libraryDependencies += "au.id.tmm.tmm-utils" %% "tmm-utils-testing" % tmmUtilsVersion  % Test,
  )
  .dependsOn(core)

lazy val cats = project
  .in(file("cats"))
  .settings(name := "tmm-scala-collections-cats")
  .settings(
    libraryDependencies += "org.typelevel" %% "cats-core" % catsVersion,
  )
  .settings(
    libraryDependencies += "org.scalatest"       %% "scalatest"              % scalatestVersion % Test,
    libraryDependencies += "au.id.tmm.tmm-utils" %% "tmm-utils-testing"      % tmmUtilsVersion  % Test,
    libraryDependencies += "org.typelevel"       %% "cats-testkit"           % catsVersion      % Test,
    libraryDependencies += "org.typelevel"       %% "cats-testkit-scalatest" % "1.0.1"          % Test,
  )
  .dependsOn(core, scalaCheck % "compile->test")

lazy val circe = project
  .in(file("circe"))
  .settings(name := "tmm-scala-collections-circe")
  .settings(
    libraryDependencies += "io.circe" %% "circe-core" % circeVersion,
  )
  .settings(
    libraryDependencies += "org.scalatest"       %% "scalatest"         % scalatestVersion % Test,
    libraryDependencies += "au.id.tmm.tmm-utils" %% "tmm-utils-testing" % tmmUtilsVersion  % Test,
    libraryDependencies += "io.circe"            %% "circe-testing"     % circeVersion     % Test,
  )
  .dependsOn(core, scalaCheck % "compile->test", cats % "compile->test")
