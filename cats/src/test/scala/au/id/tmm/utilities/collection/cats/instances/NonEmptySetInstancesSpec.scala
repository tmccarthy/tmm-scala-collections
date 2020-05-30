package au.id.tmm.utilities.collection.cats.instances

import au.id.tmm.utilities.collection.NonEmptySet
import au.id.tmm.utilities.collection.cats.instances.nonEmptySet._
import au.id.tmm.utilities.collection.cats.instances.nonEmptySet.unlawful._
import org.scalatest.flatspec.AnyFlatSpec
import cats.syntax.show.toShow
import cats.syntax.traverse.toTraverseOps
import cats.instances.option.catsStdInstancesForOption

class NonEmptySetInstancesSpec extends AnyFlatSpec {

  "the show for a NonEmptySet" should "produce a sensible string" in {
    assert(NonEmptySet.of(1, 2).show === "NonEmptySet(1, 2)")
  }

  "the traverse for a NonEmptySet" should "traverse the set" in {
    val set       = NonEmptySet.of(1, 2, 3)
    val traversed = set.traverse(Some(_): Option[Int])
    assert(traversed === Some(NonEmptySet.of(1, 2, 3)))
  }

}
