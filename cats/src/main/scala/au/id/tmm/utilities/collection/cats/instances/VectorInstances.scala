package au.id.tmm.utilities.collection.cats.instances

import au.id.tmm.utilities.collection.classes.SafeGroupBy
import cats.data.NonEmptyVector

trait VectorInstances {

  implicit val safeGroupByForVectorUsingCatsNev: SafeGroupBy[Vector, NonEmptyVector] =
    new SafeGroupBy.ForScalaIterable[Vector, NonEmptyVector] {
      override def makeNecUnsafe[A](ca: Vector[A]): NonEmptyVector[A] = NonEmptyVector.fromVectorUnsafe(ca)
    }

}
