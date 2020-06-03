package au.id.tmm.collections.cats.instances

import au.id.tmm.collections.NonEmptyMap
import au.id.tmm.collections.scalacheck.nonEmptyMap._
import au.id.tmm.collections.cats.instances.nonEmptyMap._
import au.id.tmm.utilities.testing.AdHocTestIgnore
import cats.UnorderedTraverse
import cats.data.Validated
import cats.kernel.laws.discipline.{EqTests, HashTests}
import cats.laws.discipline.{FlatMapTests, UnorderedTraverseTests}
import cats.tests.CatsSuite
import cats.laws.discipline.arbitrary._

class NonEmptyMapInstancesSpec extends CatsSuite {

  checkAll("Eq for tmmUtils NonEmptyMap", EqTests[NonEmptyMap[String, Int]].eqv)
  checkAll("Hash for tmmUtils NonEmptyMap", HashTests[NonEmptyMap[String, Int]].hash)
  checkAll("UnorderedTraverse for tmmUtils NonEmptyMap", UnorderedTraverseTests[NonEmptyMap[String, *]].unorderedTraverse[Int, Int, Int, Option, Validated[Int, *]])
  checkAll("FlatMap for tmmUtils NonEmptyMap", FlatMapTests[NonEmptyMap[String, *]].flatMap[Int, Int, Int])

}
