package au.id.tmm.collections.cats.instances

import au.id.tmm.collections.cats.instances.mapOverKeys._
import cats.laws.discipline.{FunctorTests, MonoidKTests}
import cats.tests.CatsSuite

class MapOverKeysInstancesLawsSpec extends CatsSuite {

  checkAll("Functor for Map over keys", FunctorTests[Map[*, Int]].functor[String, String, String])
  checkAll("MonoidK for Map over keys", MonoidKTests[Map[*, Int]].monoidK[String])

}
