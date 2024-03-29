package au.id.tmm.collections.cats.instances

import au.id.tmm.collections.NonEmptySet
import au.id.tmm.collections.cats.instances.nonEmptySet._
import au.id.tmm.collections.scalacheck.nonEmptySet._
import cats.data.Validated
import cats.kernel.laws.discipline.{EqTests, HashTests, SemilatticeTests}
import cats.laws.discipline.arbitrary._
import cats.laws.discipline.{
  MonadTests,
  NonEmptyTraverseTests,
  SemigroupKTests,
  SemigroupalTests,
  UnorderedTraverseTests,
}
import munit.DisciplineSuite

class NonEmptySetInstancesLawsSpec extends DisciplineSuite with AdHocTestIgnore {

  checkAll("Eq for tmmUtils NonEmptySet", EqTests[NonEmptySet[EqOnlyInt]].eqv)
  checkAll("Hash for tmmUtils NonEmptySet", HashTests[NonEmptySet[Int]](tmmUtilsHashForTmmUtilsNonEmptySet).hash)
  checkAll("Semilattice for tmmUtils NonEmptySet", SemilatticeTests[NonEmptySet[Int]].semilattice)
  checkAll("SemigroupK for tmmUtils NonEmptySet", SemigroupKTests[NonEmptySet].semigroupK[Int])
  checkAll(
    "UnorderedTraverse for tmmUtils NonEmptySet",
    UnorderedTraverseTests[NonEmptySet].unorderedTraverse[Int, Int, Int, Validated[Int, *], Option],
  )

  // Unlawful tests below

  import au.id.tmm.collections.cats.instances.unlawful.nonEmptySet._

  private implicit val nonEmptySetIsomorphism: SemigroupalTests.Isomorphisms[NonEmptySet] =
    SemigroupalTests.Isomorphisms.invariant[NonEmptySet]

  checkAll(
    "Monad for NonEmptySet",
    MonadTests[NonEmptySet].monad[Int, Int, Int],
  )
  checkAll(
    "NonEmptyTraverse for NonEmptySet",
    NonEmptyTraverseTests[NonEmptySet].nonEmptyTraverse[Option, Int, Int, Int, Int, Option, Validated[Unit, *]],
  )

  override protected val ignoredTestNames: Set[String] = Set(
    "NonEmptyTraverse for NonEmptySet.nonEmptyTraverse.traverse order consistency",
    "NonEmptyTraverse for NonEmptySet.nonEmptyTraverse.ordered constistency",
    "NonEmptyTraverse for NonEmptySet.nonEmptyTraverse.ordered consistency",
    "NonEmptyTraverse for NonEmptySet: nonEmptyTraverse.traverse order consistency",
    "NonEmptyTraverse for NonEmptySet",
    "Eq for tmmUtils NonEmptySet: eq.antisymmetry eq",
  )

}
