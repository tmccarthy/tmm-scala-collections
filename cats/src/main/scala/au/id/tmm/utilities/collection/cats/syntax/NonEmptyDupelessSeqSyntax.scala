package au.id.tmm.utilities.collection.cats.syntax

import au.id.tmm.utilities.collection.NonEmptyDupelessSeq
import cats.data.{NonEmptyList, NonEmptyVector}

class NonEmptyDupelessSeqOps[A] private[syntax] (nonEmptySet: NonEmptyDupelessSeq[A]) {

  def toNel: NonEmptyList[A] = NonEmptyList.fromListUnsafe(nonEmptySet.toList)

  def toNev: NonEmptyVector[A] = NonEmptyVector.fromVectorUnsafe(nonEmptySet.toVector)

}

object ToNonEmptyDupelessSeqOps {

  class Nel[A] private[syntax] (nel: NonEmptyList[A]) {
    def toTmmUtilsNonEmptyDupelessSeq: NonEmptyDupelessSeq[A] =
      NonEmptyDupelessSeq.fromHeadTail(nel.head, nel.tail)
  }

  class Nev[A] private[syntax] (nev: NonEmptyVector[A]) {
    def toTmmUtilsNonEmptyDupelessSeq: NonEmptyDupelessSeq[A] =
      NonEmptyDupelessSeq.fromIterableUnsafe(nev.toVector)
  }

}
