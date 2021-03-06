val settingsHelper = ProjectSettingsHelper("au.id.tmm", "tmm-scala-collections")()

settingsHelper.settingsForBuild

lazy val root = project
  .in(file("."))
  .settings(settingsHelper.settingsForRootProject)
  .settings(console := (console in Compile in core).value)
  .aggregate(
    core,
    scalaCheck,
    cats,
    circe,
  )

val catsVersion  = "2.2.0-M2"
val circeVersion = "0.14.0-M1"

lazy val core = project
  .in(file("core"))
  .settings(settingsHelper.settingsForSubprojectCalled("core"))

lazy val scalaCheck = project
  .in(file("scalacheck"))
  .settings(settingsHelper.settingsForSubprojectCalled("scalacheck"))
  .settings(
    libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.14.3",
  )
  .dependsOn(core)

lazy val cats = project
  .in(file("cats"))
  .settings(settingsHelper.settingsForSubprojectCalled("cats"))
  .settings(
    libraryDependencies += "org.typelevel" %% "cats-core"              % catsVersion,
    libraryDependencies += "org.typelevel" %% "cats-testkit"           % catsVersion % Test,
    libraryDependencies += "org.typelevel" %% "cats-testkit-scalatest" % "1.0.1"     % Test,
  )
  .dependsOn(core, scalaCheck % "compile->test")

lazy val circe = project
  .in(file("circe"))
  .settings(settingsHelper.settingsForSubprojectCalled("circe"))
  .settings(
    libraryDependencies += "io.circe" %% "circe-core"    % circeVersion,
    libraryDependencies += "io.circe" %% "circe-testing" % circeVersion % Test,
  )
  .dependsOn(core, scalaCheck % "compile->test", cats % "compile->test")

addCommandAlias("check", ";+test;scalafmtCheckAll")
