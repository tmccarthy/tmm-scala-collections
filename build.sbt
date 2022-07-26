name := "tmm-scala-collections"

ThisBuild / tlBaseVersion := "0.2"

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
  "3.1.3",
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

val catsVersion            = "2.8.0"
val circeVersion           = "0.15.0-M1"
val tmmUtilsVersion        = "0.10.0"
val mUnitVersion           = "0.7.27"
val disciplineMunitVersion = "1.0.9"

lazy val core = project
  .in(file("core"))
  .settings(name := "tmm-scala-collections-core")
  .settings(
    Compile / doc / sources := Nil, // This causes a crash so we are disabling it
  )
  .settings(
    testFrameworks += new TestFramework("munit.Framework"),
    libraryDependencies += "org.scalameta"       %% "munit"                  % mUnitVersion    % Test,
    libraryDependencies += "au.id.tmm.tmm-utils" %% "tmm-utils-testing-core" % tmmUtilsVersion % Test,
  )

lazy val scalaCheck = project
  .in(file("scalacheck"))
  .settings(name := "tmm-scala-collections-scalacheck")
  .settings(
    libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.16.0",
  )
  .settings(
    testFrameworks += new TestFramework("munit.Framework"),
    libraryDependencies += "org.scalameta"       %% "munit"                  % mUnitVersion    % Test,
    libraryDependencies += "au.id.tmm.tmm-utils" %% "tmm-utils-testing-core" % tmmUtilsVersion % Test,
  )
  .dependsOn(core)

lazy val cats = project
  .in(file("cats"))
  .settings(name := "tmm-scala-collections-cats")
  .settings(
    libraryDependencies += "org.typelevel" %% "cats-core" % catsVersion,
  )
  .settings(
    testFrameworks += new TestFramework("munit.Framework"),
    libraryDependencies += "org.scalameta"       %% "munit"                  % mUnitVersion           % Test,
    libraryDependencies += "au.id.tmm.tmm-utils" %% "tmm-utils-testing-core" % tmmUtilsVersion        % Test,
    libraryDependencies += "org.typelevel"       %% "cats-testkit"           % catsVersion            % Test,
    libraryDependencies += "org.typelevel"       %% "discipline-munit"       % disciplineMunitVersion % Test,
  )
  .dependsOn(core, scalaCheck % "compile->test")

lazy val circe = project
  .in(file("circe"))
  .settings(name := "tmm-scala-collections-circe")
  .settings(
    libraryDependencies += "io.circe" %% "circe-core" % circeVersion,
  )
  .settings(
    testFrameworks += new TestFramework("munit.Framework"),
    libraryDependencies += "org.scalameta"       %% "munit"                  % mUnitVersion    % Test,
    libraryDependencies += "au.id.tmm.tmm-utils" %% "tmm-utils-testing-core" % tmmUtilsVersion % Test,
    libraryDependencies += "io.circe"            %% "circe-testing"          % circeVersion    % Test,
  )
  .dependsOn(core, scalaCheck % "compile->test", cats % "compile->test")
