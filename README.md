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

The following collections are provided by the `tmm-scala-collections-core` artefact:

* [`DupelessSeq`](/core/src/main/scala/au/id/tmm/collections/DupelessSeq.scala): A (poorly-named) collection which can 
  be thought of as a sequence without duplicates, and with a constant-time `contains` check. It differs from [`ListSet`](https://www.scala-lang.org/api/current/scala/collection/immutable/ListSet.html)
  (and other sets that retain insertion order) in that element order is considered for equality.
* [`NonEmptyDupelessSeq`](/core/src/main/scala/au/id/tmm/collections/NonEmptyDupelessSeq.scala): A `DupelessSeq` that is
  guaranteed to be non-empty.
* [`NonEmptySet`](/core/src/main/scala/au/id/tmm/collections/NonEmptySet.scala): A set that is guaranteed to be 
  non-empty. Unlike the [`NonEmptySet`](https://typelevel.org/cats/api/cats/data/NonEmptySetOps.html) provided by [Cats](https://github.com/typelevel/cats),
  this collection relies on universal equality in the same way as the Scala set. This makes it easier to use, as 
  elements do not require an `Order` instance, but comes at the cost of strict-lawfulness.
* [`NonEmptyMap`](/core/src/main/scala/au/id/tmm/collections/NonEmptyMap.scala): A map that is guaranteed to be 
  non-empty. Again, like `NonEmptySet`, this differs from the Cats `NonEmptyMap` in that it relies on universal equality
  rather than requiring an `Order` instance for keys.
* [`NonEmptyArraySeq`](/core/src/main/scala/au/id/tmm/collections/NonEmptyArraySeq.scala): An immutable [`ArraySeq`](https://www.scala-lang.org/api/current/scala/collection/immutable/ArraySeq$.html)
  that is guaranteed to be non-empty.

#### Third-party library integration

The other modules in this project provide integration between the above collections and some popular FP libraries:

* `tmm-scala-collections-scalacheck` provides integration with [ScalaCheck](https://github.com/typelevel/scalacheck) to
  support property-based testing.
* `tmm-scala-collections-circe` provides codecs for use with the [Circe](https://github.com/circe/circe) json library.
* `tmm-scala-collections-cats` provides extensive lawful and unlawful instances for the collections above.

## Syntax

```scala
import au.id.tmm.collections.syntax._
```

The `tmm-scala-collections-core` project many useful syntaxes via the above import. Some highlights are outlined below:

#### Safe `groupBy`

The [`groupBy`](https://www.scala-lang.org/api/current/scala/collection/IterableOps.html#groupBy[K](f:A=%3EK):scala.collection.immutable.Map[K,C])
method provided by the `IterableOps` class can be improved by reflecting the non-empty nature of the groups in the type
signature. The `safeGroupBy` syntax provides this:

```scala
import au.id.tmm.collections.NonEmptySet
import au.id.tmm.collections.syntax._
import au.id.tmm.collections.cats.instances.all._
import cats.data.NonEmptyList

val list = List("apple", "apricot", "banana")
val set = Set("apple", "apricot", "banana")

// ❌ BAD, return type doesn't indicate that groups cannot be empty
val _: Map[Char, List[String]] = list.groupBy(_.head)
val _: Map[Char, Set[String]]  = set.groupBy(_.head)

// ✅ GOOD, return type indicates that groups are non-empty
val _: Map[Char, NonEmptyList[String]] = list.safeGroupBy(_.head)
val _: Map[Char, NonEmptySet[String]]  = set.safeGroupBy(_.head)
```

#### `orError` operations for `Iterable`

The `orError` syntaxes allow for easily extracting elements from `Iterable` collections based on the number of elements.
I have found this valuable when processing and cleaning data from external sources:

* `onlyElementOrException` returns a `Right` containing the element if the collection has exactly one element, `Left` 
  otherwise.
* `emptyOrException` returns `Right(())` if the collection is empty, `Left` otherwise
* `atMostOne` returns `Right(None)` for empty collections, `Right(Some)` for those with one element, and `Left` for two
   or more elements.