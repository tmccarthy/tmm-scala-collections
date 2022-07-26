package au.id.tmm.collections.cats.instances.unlawful

import au.id.tmm.collections.NonEmptyMap
import au.id.tmm.collections.cats.instances.nonEmptyMap._
import au.id.tmm.collections.cats.instances.unlawful.nonEmptyMap._
import au.id.tmm.collections.scalacheck.nonEmptyMap._
import cats.data.Validated
import cats.laws.discipline.TraverseTests
import cats.laws.discipline.arbitrary._
import munit.DisciplineSuite

class NonEmptyMapUnlawfulInstancesSpec extends DisciplineSuite {

  checkAll(
    "Traverse for NonEmptyMap",
    TraverseTests[NonEmptyMap[String, *]].traverse[Int, Int, Int, Int, Option, Validated[Unit, *]],
  )

}
