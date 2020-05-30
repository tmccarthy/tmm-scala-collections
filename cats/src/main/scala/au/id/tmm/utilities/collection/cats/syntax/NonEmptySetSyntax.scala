package au.id.tmm.utilities.collection.cats.syntax

import au.id.tmm.utilities.collection.NonEmptySet
import cats.data.{NonEmptyList, NonEmptyVector}

class NonEmptySetOps[A] private[syntax] (nonEmptySet: NonEmptySet[A]) {

  def toNel: NonEmptyList[A] = NonEmptyList.fromListUnsafe(nonEmptySet.toList)

  def toNev: NonEmptyVector[A] = NonEmptyVector.fromVectorUnsafe(nonEmptySet.toVector)

}

object ToNonEmptySetOps {

  class Nel[A] private[syntax] (nel: NonEmptyList[A]) {
    def toTmmUtilsNonEmptySet: NonEmptySet[A] =
      NonEmptySet.fromHeadTail(nel.head, nel.tail)
  }

  class Nev[A] private[syntax] (nev: NonEmptyVector[A]) {
    def toTmmUtilsNonEmptySet: NonEmptySet[A] =
      NonEmptySet.fromIterableUnsafe(nev.toVector)
  }

}
