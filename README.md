# `tmm-scala-collections`
[![CircleCI](https://circleci.com/gh/tmccarthy/tmm-scala-collections.svg?style=svg)](https://app.circleci.com/pipelines/github/tmccarthy/tmm-scala-collections)
[![Maven Central](https://img.shields.io/maven-central/v/au.id.tmm.tmm-scala-collections/tmm-scala-collections-core_2.13.svg)](https://repo.maven.apache.org/maven2/au/id/tmm/tmm-scala-collections/tmm-scala-collections-core_2.13/)

This is a set of libraries that contain Scala collections and collection-oriented utilities that I've found useful. It 
was previous a sub-project of [tmccarthy/tmmUtils](https://github.com/tmccarthy/tmmUtils), but it has reached a level of
maturity that justifies splitting it out.

## Usage

The libraries have been published to Maven central. They can be used by adding the following to your `build.sbt`

```scala
val tmmCollectionsVersion = "0.0.1"

libraryDependencies += "au.id.tmm.tmm-scala-collections" %% "tmm-scala-collections-core"       % tmmCollectionsVersion,
libraryDependencies += "au.id.tmm.tmm-scala-collections" %% "tmm-scala-collections-circe"      % tmmCollectionsVersion,
libraryDependencies += "au.id.tmm.tmm-scala-collections" %% "tmm-scala-collections-cats"       % tmmCollectionsVersion,
libraryDependencies += "au.id.tmm.tmm-scala-collections" %% "tmm-scala-collections-scalacheck" % tmmCollectionsVersion % Test, 
```

## Collections

