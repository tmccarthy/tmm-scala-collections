package au.id.tmm.collections

import scala.annotation.unchecked.uncheckedVariance
import scala.collection.{Factory, IterableOps, MapView, View, WithFilter, mutable}
import scala.reflect.ClassTag

//trait NonEmptyIterableOps2[A, NEC_C[_], C_C[_], +C <: IterableOps[A, C_C, C]] extends IterableOnce[A] {
//
//  def underlying: C
//  protected def unwrap[X](necX: NEC_C[X]): C_C[X]
//  def companion: NonEmptyIterableCompanion2[NEC_C, C_C]
//
//  protected def constructor[X](cx: C_C[X]): NEC_C[X] = companion.constructor(cx)
//  def nonEmptyIterableFactory: NonEmptyIterableCompanion2[NEC_C, C_C] = companion
//
//  def view: View[A] = underlying.view
//
//  def iterator: Iterator[A] = underlying.iterator
//
//  def head: A = underlying.head
//
//  def last: A = underlying.last
//
//  def transpose[B](implicit asIterable: A <:< Iterable[B]): C_C[NEC_C[B]] = underlying.transpose.map(constructor)
//
//}
//
//trait NonEmptyIterableCompanion2[NEC_C[_], C_C[_]] {
//  protected[collections] def className: String
//  protected[collections] def constructor[A](ca: C_C[A]): NEC_C[A]
//  protected def newUnderlyingBuilder[A]: mutable.Builder[A, C_C[A]]
//
//  def one[A](head: A): NEC_C[A]
//}

trait NonEmptyIterableOps[C[X] <: IterableOps[X, C, C[X]], NEC[_], +A] extends IterableOnce[A] {

  def underlying: C[A @uncheckedVariance]
  protected def unwrap[X](necX: NEC[X]): C[X]
  def companion: NonEmptyIterableCompanion[C, NEC]

  protected def constructor[X](cx: C[X]): NEC[X]                 = companion.constructor(cx)
  def nonEmptyIterableFactory: NonEmptyIterableCompanion[C, NEC] = companion

  protected def className: String = companion.className

  def view: View[A] = underlying.view

  def iterator: Iterator[A] = underlying.iterator

  def head: A = underlying.head

  def last: A = underlying.last

  def transpose[B](implicit asIterable: A <:< Iterable[B]): C[NEC[B]] = underlying.transpose.map(constructor)

  def filter(pred: A => Boolean): C[A @uncheckedVariance] = underlying.filter(pred)

  def filterNot(pred: A => Boolean): C[A @uncheckedVariance] = underlying.filterNot(pred)

  def withFilter(p: A => Boolean): WithFilter[A, C] = underlying.withFilter(p)

  def partition(p: A => Boolean): (C[A @uncheckedVariance], C[A @uncheckedVariance]) = underlying.partition(p)

  def splitAt(n: Int): (C[A @uncheckedVariance], C[A @uncheckedVariance]) = underlying.splitAt(n)

  def take(n: Int): C[A @uncheckedVariance] = underlying.take(n)

  def takeRight(n: Int): C[A @uncheckedVariance] = underlying.takeRight(n)

  def takeWhile(p: A => Boolean): C[A @uncheckedVariance] = underlying.takeWhile(p)

  def span(p: A => Boolean): (C[A @uncheckedVariance], C[A @uncheckedVariance]) = underlying.span(p)

  def drop(n: Int): C[A @uncheckedVariance] = underlying.drop(n)

  def dropRight(n: Int): C[A @uncheckedVariance] = underlying.dropRight(n)

  def dropWhile(p: A => Boolean): C[A @uncheckedVariance] = underlying.dropWhile(p)

  def grouped(size: Int): Iterator[C[A @uncheckedVariance]] = underlying.grouped(size)

  def sliding(size: Int): Iterator[C[A @uncheckedVariance]] = underlying.sliding(size)

  def sliding(size: Int, step: Int): Iterator[C[A @uncheckedVariance]] = underlying.sliding(size, step)

  def tail: C[A @uncheckedVariance] = underlying.tail

  def init: C[A @uncheckedVariance] = underlying.init

  def slice(from: Int, until: Int): C[A @uncheckedVariance] = underlying.slice(from, until)

  def groupBy[K](f: A => K): Map[K, NEC[A @uncheckedVariance]] = {
    val grouped: Map[K, C[A]]                        = underlying.groupBy[K](f)
    val groupedView: MapView[K, C[A]]                = grouped.view
    val groupedViewWithNecValues: MapView[K, NEC[A]] = groupedView.mapValues[NEC[A @uncheckedVariance]](constructor)
    val asMap: Map[K, NEC[A]]                        = groupedViewWithNecValues.toMap[K, NEC[A @uncheckedVariance]]

    asMap
  }

  def groupMap[K, B](key: A => K)(f: A => B): Map[K, NEC[B @uncheckedVariance]] = {
    val groupMapped: Map[K, C[B]]         = underlying.groupMap[K, B](key)(f)
    val groupMappedView: MapView[K, C[B]] = groupMapped.view
    val groupMappedViewWithNecValues: MapView[K, NEC[B]] =
      groupMappedView.mapValues[NEC[B @uncheckedVariance]](constructor)
    val asMap: Map[K, NEC[B]] = groupMappedViewWithNecValues.toMap[K, NEC[B @uncheckedVariance]]

    asMap
  }

  def groupMapReduce[K, B](key: A => K)(f: A => B)(reduce: (B, B) => B): Map[K, B] =
    underlying.groupMapReduce(key)(f)(reduce)

  def scan[B >: A](z: B)(op: (B, B) => B): NEC[B] = constructor(underlying.scan(z)(op))

  def scanLeft[B](z: B)(op: (B, A) => B): NEC[B] = constructor(underlying.scanLeft(z)(op))

  def scanRight[B](z: B)(op: (A, B) => B): NEC[B] = constructor(underlying.scanRight(z)(op))

  def map[B](f: A => B): NEC[B] = constructor(underlying.map(f))

  def flatMap[B](f: A => NEC[B]): NEC[B] = constructor(underlying.flatMap(f.andThen(unwrap)))

  def flatMap[B](f: A => IterableOnce[B]): C[B] = underlying.flatMap(f)

  def flatten[B](implicit asNec: A <:< NEC[B]): NEC[B] = constructor(underlying.flatten[B](unwrap(_)))

  def collect[B](pf: PartialFunction[A, B]): C[B] = underlying.collect(pf)

  def partitionMap[A1, A2](f: A => Either[A1, A2]): (C[A1], C[A2]) = underlying.partitionMap(f)

  def concat[B >: A](suffix: IterableOnce[B]): NEC[B] = constructor(underlying.concat(suffix))

  def concat[B >: A](suffix: NEC[B]): NEC[B] = constructor(underlying.concat(unwrap(suffix)))

  def zip[B](that: NEC[B @uncheckedVariance]): NEC[(A, B) @uncheckedVariance] = constructor(underlying zip unwrap(that))

  def zip[B](that: IterableOnce[B]): C[(A, B) @uncheckedVariance] = underlying.zip(that)

  def zipWithIndex: NEC[(A, Int) @uncheckedVariance] = constructor(underlying.zipWithIndex)

  def zipAll[A1 >: A, B](
    that: Iterable[B],
    thisElem: A1,
    thatElem: B,
  ): NEC[(A1, B)] =
    constructor(underlying.zipAll(that, thisElem, thatElem))

  def unzip[A1, A2](implicit asPair: A <:< (A1, A2)): (NEC[A1], NEC[A2]) =
    underlying.unzip match {
      case (l, r) => (constructor(l), constructor(r))
    }

  def unzip3[A1, A2, A3](implicit asTriple: A <:< (A1, A2, A3)): (NEC[A1], NEC[A2], NEC[A3]) =
    underlying.unzip3 match {
      case (l, c, r) => (constructor(l), constructor(c), constructor(r))
    }

  def tails: Iterator[C[A @uncheckedVariance]] = underlying.tails

  def inits: Iterator[C[A @uncheckedVariance]] = underlying.inits

  def tapEach[U](f: A => U): NEC[A @uncheckedVariance] = constructor(underlying.tapEach(f))

  def hashCode(): Int

  def equals(obj: Any): Boolean

  override def toString: String = underlying.mkString(s"$className(", ", ", ")")

  def foreach[U](f: A => U): Unit = underlying.foreach(f)

  def forall(p: A => Boolean): Boolean = underlying.forall(p)

  def exists(p: A => Boolean): Boolean = underlying.exists(p)

  def count(p: A => Boolean): Int = underlying.count(p)

  def find(p: A => Boolean): Option[A] = underlying.find(p)

  def foldLeft[B](z: B)(op: (B, A) => B): B = underlying.foldLeft(z)(op)

  def foldRight[B](z: B)(op: (A, B) => B): B = underlying.foldRight(z)(op)

  def fold[A1 >: A](z: A1)(op: (A1, A1) => A1): A1 = underlying.fold(z)(op)

  def reduce[B >: A](op: (B, B) => B): B = underlying.reduce(op)

  def reduceLeft[B >: A](op: (B, A) => B): B = underlying.reduceLeft(op)

  def reduceRight[B >: A](op: (A, B) => B): B = underlying.reduceRight(op)

  def isEmpty: Boolean = false

  def nonEmpty: Boolean = true

  def size: Int = underlying.size

  def copyToArray[B >: A](xs: Array[B]): Int = underlying.copyToArray(xs)

  def copyToArray[B >: A](xs: Array[B], start: Int): Int = underlying.copyToArray(xs, start)

  def copyToArray[B >: A](
    xs: Array[B],
    start: Int,
    len: Int,
  ): Int = underlying.copyToArray(xs, start, len)

  def sum[B >: A](implicit num: Numeric[B]): B = underlying.sum[B]

  def product[B >: A](implicit num: Numeric[B]): B = underlying.product[B]

  def min[B >: A](implicit ord: Ordering[B]): A = underlying.min[B]

  def max[B >: A](implicit ord: Ordering[B]): A = underlying.max[B]

  def maxBy[B](f: A => B)(implicit cmp: Ordering[B]): A = underlying.maxBy[B](f)

  def minBy[B](f: A => B)(implicit cmp: Ordering[B]): A = underlying.minBy[B](f)

  def collectFirst[B](pf: PartialFunction[A, B]): Option[B] = underlying.collectFirst(pf)

  def corresponds[B](that: IterableOnce[B])(p: (A, B) => Boolean): Boolean = underlying.corresponds(that)(p)

  def to[C1](factory: Factory[A, C1]): C1 = underlying.to(factory)

  def toList: List[A]                                   = underlying.toList
  def toVector: Vector[A]                               = underlying.toVector
  def toMap[K, V](implicit ev: A <:< (K, V)): Map[K, V] = underlying.toMap
  def toSet[B >: A]: Set[B]                             = underlying.toSet
  def toSeq: Seq[A]                                     = underlying.toSeq
  def toIndexedSeq: IndexedSeq[A]                       = underlying.toIndexedSeq

  def toNonEmptyMap[K, V](implicit ev: A <:< (K, V)): NonEmptyMap[K, V] =
    NonEmptyMap.fromIterableUnsafe[K, V](underlying.to(Iterable).asInstanceOf[Iterable[(K, V)]])

  def toNonEmptyArraySeq(implicit ev: ClassTag[A] @uncheckedVariance): NonEmptyArraySeq[A] =
    NonEmptyArraySeq.fromIterableUnsafe(underlying.to(Iterable))

  def toUntaggedNonEmptyArraySeq: NonEmptyArraySeq[A] =
    NonEmptyArraySeq.untagged.fromIterableUnsafe(underlying.to(Iterable))

  def toNonEmptyDupelessSeq: NonEmptyDupelessSeq[A] =
    NonEmptyDupelessSeq.fromIterableUnsafe(underlying.to(Iterable))

  def toNonEmptySet[B >: A]: NonEmptySet[B] =
    NonEmptySet.fromIterableUnsafe(underlying.to(Iterable))

  def toArray[B >: A](implicit evidence$2: ClassTag[B]): Array[B] = underlying.toArray[B]

}

trait NonEmptyIterableCompanion[C[X] <: IterableOps[X, C, C[X]], NEC[_]] {

  protected[collections] def className: String
  protected[collections] def constructor[A](ca: C[A]): NEC[A]
  protected def newUnderlyingBuilder[A]: mutable.Builder[A, C[A]]

  def one[A](head: A): NEC[A]

  def fromHeadTail[A](head: A, tail: Iterable[A]): NEC[A] = {
    val builder = newUnderlyingBuilder[A].addOne(head)
    builder.addAll(tail)
    constructor(builder.result())
  }

  def of[A](head: A, tail: A*): NEC[A] = fromHeadTail(head, tail)

  // TODO should be protected once we have a proper typeclass to abstract over this stuff
  def fromUnderlying[A](underlying: C[A]): Option[NEC[A]] =
    if (underlying.isEmpty) None else Some(constructor(underlying))

  // TODO should be protected once we have a proper typeclass to abstract over this stuff
  def fromUnderlyingUnsafe[A](underlying: C[A]): NEC[A] =
    if (underlying.isEmpty)
      throw new IllegalArgumentException("Cannot create NEC from empty set")
    else
      constructor(underlying)

  def fromIterable[A](iterable: IterableOnce[A]): Option[NEC[A]] = {
    val builder = newUnderlyingBuilder[A]
    builder.addAll(iterable)
    fromUnderlying(builder.result())
  }

  def fromIterableUnsafe[A](iterable: IterableOnce[A]): NEC[A] =
    fromIterable(iterable).getOrElse(
      throw new IllegalArgumentException("Cannot create NEC from empty set"),
    )

  def fromCons[A](cons: ::[A]): NEC[A] =
    fromHeadTail(cons.head, cons.tail)

  def unsafeBuilder[A]: mutable.Builder[A, NEC[A]] =
    new mutable.Builder[A, NEC[A]] {
      private val underlyingBuilder: mutable.Builder[A, C[A]] = newUnderlyingBuilder[A]
      override def clear(): Unit                              = underlyingBuilder.clear()
      override def result(): NEC[A]                           = fromUnderlyingUnsafe(underlyingBuilder.result())
      override def addOne(elem: A): this.type = {
        underlyingBuilder.addOne(elem)
        this
      }
      override def addAll(xs: IterableOnce[A]): this.type = {
        underlyingBuilder.addAll(xs)
        this
      }
      override def sizeHint(size: Int): Unit = underlyingBuilder.sizeHint(size)
      override def knownSize: Int            = underlyingBuilder.knownSize
    }

}
