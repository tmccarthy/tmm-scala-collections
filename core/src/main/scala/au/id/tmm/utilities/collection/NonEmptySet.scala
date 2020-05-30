package au.id.tmm.utilities.collection

import scala.annotation.unchecked.uncheckedVariance
import scala.collection.immutable.Set
import scala.collection.mutable

/**
  * A Set that is guaranteed to be non-empty. Equality of elements is based on universal equality.
  */
final class NonEmptySet[A] private (val underlying: Set[A])
    extends (A => Boolean)
    // format: off
    with NonEmptyIterableOps[
      ({ type λ[+ ⍺] = Set[⍺ @uncheckedVariance] })#λ,
      ({ type λ[+ ⍺] = NonEmptySet[⍺ @uncheckedVariance] })#λ,
      A @uncheckedVariance,
    ] {
  // format: on

  override protected def constructor[X](cx: Set[X]): NonEmptySet[X] = new NonEmptySet[X](cx)

  override def companion: NonEmptyIterableCompanion[Set, NonEmptySet] = NonEmptySet

  override protected def unwrap[X](necX: NonEmptySet[X]): Set[X] = necX.underlying

  def contains(elem: A): Boolean = underlying.contains(elem)

  def diff(that: collection.Set[A]): Set[A] = underlying.diff(that)

  def subsetOf(that: collection.Set[A]): Boolean = underlying.subsetOf(that)

  def subsets(len: Int): Iterator[Set[A]] = underlying.subsets(len)

  def subsets(): Iterator[Set[A]] = underlying.subsets()

  def intersect(that: collection.Set[A]): Set[A] = underlying.intersect(that)

  def --(that: IterableOnce[A]): Set[A] = underlying.--(that)

  def --(that: NonEmptySet[A]): Set[A] = underlying.--(that.underlying)

  def -(elem: A): Set[A] = underlying.-(elem)

  def incl(elem: A): NonEmptySet[A] = constructor(underlying.incl(elem))

  def excl(elem: A): Set[A] = underlying.excl(elem)

  def removedAll(that: IterableOnce[A]): Set[A] = underlying.removedAll(that)

  def removedAll(that: NonEmptySet[A]): Set[A] = underlying.removedAll(that.underlying)

  def concat(that: IterableOnce[A]): NonEmptySet[A] = constructor(underlying.concat(that))

  def concat(that: NonEmptySet[A]): NonEmptySet[A] = constructor(underlying.concat(that.underlying))

  def +(elem: A): NonEmptySet[A] = constructor(underlying.+(elem))

  def ++(that: IterableOnce[A]): NonEmptySet[A] = concat(that)

  def ++(that: NonEmptySet[A]): NonEmptySet[A] = concat(that)

  override def apply(a: A): Boolean = contains(a)

  override def equals(other: Any): Boolean = other match {
    case that: NonEmptySet[_] =>
      this.underlying == that.underlying
    case _ => false
  }

  override def hashCode(): Int = underlying.hashCode()

}

object NonEmptySet extends NonEmptyIterableCompanion[Set, NonEmptySet] {

  override protected[collection] def className: String = "NonEmptySet"

  override protected[collection] def constructor[A](ca: Set[A]): NonEmptySet[A] = new NonEmptySet[A](ca)

  override protected def newUnderlyingBuilder[A]: mutable.Builder[A, Set[A]] = Set.newBuilder

  override def one[A](head: A): NonEmptySet[A] = new NonEmptySet[A](Set(head))

  def fromSet[A](set: Set[A]): Option[NonEmptySet[A]] = this.fromUnderlying(set)

  def fromSetUnsafe[A](set: Set[A]): NonEmptySet[A] = this.fromUnderlyingUnsafe(set)

  override def fromIterable[A](iterable: IterableOnce[A]): Option[NonEmptySet[A]] =
    iterable match {
      case s: Set[A]      => fromSet(s)
      case i: Iterable[A] => super.fromIterable(i)
    }

}
