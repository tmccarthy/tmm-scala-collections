import sbt.Keys.libraryDependencies
import sbt._
import sbt.librarymanagement.{CrossVersion, ModuleID}

object DependencySettings {

  private val silencerVersion = "1.4.1" // TODO upgrade this

  val commonDependencies: Seq[Def.Setting[Seq[ModuleID]]] = Seq(
    libraryDependencies += "org.scalatest"       %% "scalatest"         % "3.0.8" % Test, // TODO upgrade this
    libraryDependencies += "au.id.tmm.tmm-utils" %% "tmm-utils-testing" % "0.4.7" % Test,
    libraryDependencies += "com.github.ghik"     %% "silencer-lib"      % silencerVersion % Provided,

    libraryDependencies += compilerPlugin("com.github.ghik" %% "silencer-plugin" % silencerVersion),
  )

}
