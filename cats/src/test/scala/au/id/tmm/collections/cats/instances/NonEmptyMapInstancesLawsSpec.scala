package au.id.tmm.collections.cats.instances

import au.id.tmm.collections.NonEmptyMap
import au.id.tmm.collections.cats.instances.nonEmptyMap._
import au.id.tmm.collections.scalacheck.nonEmptyMap._
import cats.data.Validated
import cats.kernel.laws.discipline.{CommutativeSemigroupTests, EqTests, HashTests, SemigroupTests}
import cats.laws.discipline.arbitrary._
import cats.laws.discipline.{AlignTests, FunctorTests, SemigroupKTests, UnorderedTraverseTests}
import cats.tests.CatsSuite

class NonEmptyMapInstancesLawsSpec extends CatsSuite {

  checkAll(
    "Eq for tmmUtils NonEmptyMap",
    EqTests[NonEmptyMap[String, EqOnlyInt]].eqv,
  )

  checkAll(
    "Hash for tmmUtils NonEmptyMap",
    HashTests[NonEmptyMap[String, Int]].hash,
  )

  checkAll(
    "UnorderedTraverse for tmmUtils NonEmptyMap",
    UnorderedTraverseTests[NonEmptyMap[String, *]].unorderedTraverse[Int, Int, Int, Option, Validated[Int, *]],
  )

  checkAll(
    "Functor for tmmUtils NonEmptyMap",
    FunctorTests[NonEmptyMap[String, *]].functor[Int, Int, Int],
  )

  checkAll(
    "Align for tmmUtils NonEmptyMap",
    AlignTests[NonEmptyMap[String, *]].align[Int, Int, Int, Int],
  )

  checkAll(
    "CommutativeSemigroup for tmmUtils NonEmptyMap",
    CommutativeSemigroupTests[NonEmptyMap[String, Int]].commutativeSemigroup,
  )

  checkAll(
    "Semigroup for tmmUtils NonEmptyMap",
    SemigroupTests[NonEmptyMap[String, Int]].semigroup,
  )

  checkAll(
    "Functor over keys for tmmUtils NonEmptyMap",
    FunctorTests[NonEmptyMap[*, Int]].functor[String, String, String],
  )

  checkAll(
    "SemigroupK over keys for tmmUtils NonEmptyMap",
    SemigroupKTests[NonEmptyMap[*, Int]].semigroupK[String],
  )

}
