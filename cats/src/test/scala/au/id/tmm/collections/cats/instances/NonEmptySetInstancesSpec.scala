package au.id.tmm.collections.cats.instances

import au.id.tmm.collections.NonEmptySet
import au.id.tmm.collections.cats.instances.nonEmptySet._
import au.id.tmm.collections.cats.instances.unlawful.nonEmptySet._
import cats.instances.option.catsStdInstancesForOption
import cats.syntax.show.toShow
import cats.syntax.traverse.toTraverseOps
import munit.FunSuite

class NonEmptySetInstancesSpec extends FunSuite {

  test("the show for a NonEmptySet should produce a sensible string") {
    assertEquals(NonEmptySet.of(1, 2).show, "NonEmptySet(1, 2)")
  }

  test("the traverse for a NonEmptySet should traverse the set") {
    val set       = NonEmptySet.of(1, 2, 3)
    val traversed = set.traverse(Some(_): Option[Int])
    assertEquals(traversed, Some(NonEmptySet.of(1, 2, 3)))
  }

}
