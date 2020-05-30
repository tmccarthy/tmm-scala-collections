package au.id.tmm.utilities.collection

import scala.collection.immutable.ArraySeq
import scala.collection.mutable
import scala.reflect.ClassTag

// TODO what would this look like as an AnyRef wrapper?
final class NonEmptyArraySeq[+A] private (val underlying: ArraySeq[A])
    extends NonEmptySeqOps[ArraySeq, NonEmptyArraySeq, A] {
  override protected def constructor[X](cx: ArraySeq[X]): NonEmptyArraySeq[X] = new NonEmptyArraySeq[X](cx)

  override protected def unwrap[X](necX: NonEmptyArraySeq[X]): ArraySeq[X] = necX.underlying

  override def companion: NonEmptyIterableCompanion[ArraySeq, NonEmptyArraySeq] = NonEmptyArraySeq.untagged

  override def equals(other: Any): Boolean = other match {
    case that: NonEmptyArraySeq[_] =>
      this.underlying == that.underlying
    case _ => false
  }

  override def hashCode(): Int = underlying.hashCode()

}

object NonEmptyArraySeq {

  // The methods from NonEmptyIterableCompanion are re-implemented here so as to use class tags to construct specialised
  // ArraySeq instances

  def one[A : ClassTag](a: A): NonEmptyArraySeq[A] =
    new NonEmptyArraySeq[A](ArraySeq[A](a))

  def fromHeadTail[A : ClassTag](head: A, tail: Iterable[A]): NonEmptyArraySeq[A] =
    new NonEmptyArraySeq[A](
      ArraySeq
        .newBuilder[A]
        .addOne(head)
        .addAll(tail)
        .result(),
    )

  def of[A : ClassTag](head: A, tail: A*): NonEmptyArraySeq[A] =
    fromHeadTail[A](head, tail)

  def unsafeWrapArray[A](array: Array[A]): Option[NonEmptyArraySeq[A]] =
    if (array.isEmpty) {
      None
    } else {
      Some(new NonEmptyArraySeq(ArraySeq.unsafeWrapArray(array)))
    }

  def fromArraySeq[A](arraySeq: ArraySeq[A]): Option[NonEmptyArraySeq[A]] = untagged.fromArraySeq(arraySeq)

  def fromArraySeqUnsafe[A](arraySeq: ArraySeq[A]): NonEmptyArraySeq[A] = untagged.fromArraySeqUnsafe(arraySeq)

  def fromIterable[A : ClassTag](iterable: Iterable[A]): Option[NonEmptyArraySeq[A]] = iterable match {
    case arraySeq: ArraySeq[A] => fromArraySeq[A](arraySeq)
    case _                     => fromArraySeq(ArraySeq.newBuilder[A].addAll(iterable).result())
  }

  def fromIterableUnsafe[A : ClassTag](iterable: Iterable[A]): NonEmptyArraySeq[A] =
    fromIterable[A](iterable).getOrElse(
      throw new IllegalArgumentException("Cannot create NonEmptyArraySeq from empty set"),
    )

  object untagged extends NonEmptyIterableCompanion[ArraySeq, NonEmptyArraySeq] {
    override protected[collection] def className: String = "NonEmptyArraySeq"

    override protected[collection] def constructor[A](ca: ArraySeq[A]): NonEmptyArraySeq[A] =
      new NonEmptyArraySeq[A](ca)

    override protected def newUnderlyingBuilder[A]: mutable.Builder[A, ArraySeq[A]] = ArraySeq.untagged.newBuilder

    override def one[A](head: A): NonEmptyArraySeq[A] = new NonEmptyArraySeq(ArraySeq.untagged(head))

    def fromArraySeq[A](arraySeq: ArraySeq[A]): Option[NonEmptyArraySeq[A]] = this.fromUnderlying(arraySeq)

    def fromArraySeqUnsafe[A](arraySeq: ArraySeq[A]): NonEmptyArraySeq[A] = this.fromUnderlyingUnsafe(arraySeq)

    override def fromIterable[A](iterable: IterableOnce[A]): Option[NonEmptyArraySeq[A]] =
      iterable match {
        case s: ArraySeq[A] => fromArraySeq(s)
        case i: Iterable[A] => super.fromIterable(i)
      }
  }

}
