package au.id.tmm.utilities.collection.cats.instances

import au.id.tmm.utilities.collection.classes.SafeGroupBy
import cats.data.NonEmptyList

trait ListInstances {

  implicit val safeGroupByForListUsingCatsNel: SafeGroupBy[List, NonEmptyList] =
    new SafeGroupBy.ForScalaIterable[List, NonEmptyList] {
      override def makeNecUnsafe[A](ca: List[A]): NonEmptyList[A] = NonEmptyList.fromListUnsafe(ca)
    }

}
