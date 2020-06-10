package au.id.tmm.collections

import scala.annotation.unchecked.uncheckedVariance
import scala.collection.immutable.ArraySeq
import scala.collection.mutable
import scala.reflect.ClassTag

class NonEmptyDupelessSeq[+A] private (val underlying: DupelessSeq[A])
    extends NonEmptySeqOps[DupelessSeq, NonEmptyDupelessSeq, A] {

  override protected def constructor[X](cx: DupelessSeq[X]): NonEmptyDupelessSeq[X] = new NonEmptyDupelessSeq[X](cx)

  override protected def unwrap[X](necX: NonEmptyDupelessSeq[X]): DupelessSeq[X] = necX.underlying

  override def companion: NonEmptyIterableCompanion[DupelessSeq, NonEmptyDupelessSeq] = NonEmptyDupelessSeq

  def -[A1 >: A](elem: A1): DupelessSeq[A] =
    underlying.-(elem)

  override def toSet[B >: A]: Set[B] = underlying.toSet

  override def toNonEmptyArraySeq(implicit ev: ClassTag[A] @uncheckedVariance): NonEmptyArraySeq[A] =
    NonEmptyArraySeq.fromIterableUnsafe(underlying.toArraySeq)

  override def toNonEmptyDupelessSeq: NonEmptyDupelessSeq[A] =
    this

  override def toNonEmptySet[B >: A]: NonEmptySet[B] = NonEmptySet.fromSetUnsafe(underlying.toSet)

  def toArraySeq: ArraySeq[A] = underlying.toArraySeq

  override def equals(other: Any): Boolean =
    other match {
      case that: NonEmptyDupelessSeq[_] =>
        this.underlying == that.underlying
      case _ => false
    }

  override def hashCode(): Int = underlying.hashCode()

}

object NonEmptyDupelessSeq extends NonEmptyIterableCompanion[DupelessSeq, NonEmptyDupelessSeq] {

  override protected[collections] def className: String = "NonEmptyDupelessSeq"

  override protected[collections] def constructor[A](ca: DupelessSeq[A]): NonEmptyDupelessSeq[A] =
    new NonEmptyDupelessSeq[A](ca)

  override protected def newUnderlyingBuilder[A]: mutable.Builder[A, DupelessSeq[A]] = DupelessSeq.newBuilder

  override def one[A](head: A): NonEmptyDupelessSeq[A] = new NonEmptyDupelessSeq[A](DupelessSeq(head))

  def fromDupelessSeq[A](set: DupelessSeq[A]): Option[NonEmptyDupelessSeq[A]] = this.fromUnderlying(set)

  def fromDupelessSeqUnsafe[A](set: DupelessSeq[A]): NonEmptyDupelessSeq[A] = this.fromUnderlyingUnsafe(set)

  override def fromIterable[A](iterable: IterableOnce[A]): Option[NonEmptyDupelessSeq[A]] =
    // TODO consider optimizing the case where we are constructing from an ArraySeq
    iterable match {
      case s: DupelessSeq[A] => fromDupelessSeq(s)
      case i: Iterable[A]    => super.fromIterable(i)
    }

}
