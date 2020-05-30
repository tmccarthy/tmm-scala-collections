package au.id.tmm.utilities.collection

import scala.collection.{Factory, IterableOps, View, WithFilter, mutable}
import scala.reflect.ClassTag

trait NonEmptyIterableOps[C[+X] <: IterableOps[X, C, C[X]], NEC[+_], +A] extends IterableOnce[A] {

  def underlying: C[A]
  protected def unwrap[X](necX: NEC[X]): C[X]
  def companion: NonEmptyIterableCompanion[C, NEC]

  protected def constructor[X](cx: C[X]): NEC[X]                 = companion.constructor(cx)
  def nonEmptyIterableFactory: NonEmptyIterableCompanion[C, NEC] = companion

  protected def className: String = companion.className

  def view: View[A] = underlying.view

  def toIterable: Iterable[A] = underlying.toIterable

  def iterator: Iterator[A] = underlying.iterator

  def head: A = underlying.head

  def last: A = underlying.last

  def transpose[B](implicit asIterable: A <:< Iterable[B]): C[NEC[B]] = underlying.transpose.map(constructor)

  def filter(pred: A => Boolean): C[A] = underlying.filter(pred)

  def filterNot(pred: A => Boolean): C[A] = underlying.filterNot(pred)

  def withFilter(p: A => Boolean): WithFilter[A, C] = underlying.withFilter(p)

  def partition(p: A => Boolean): (C[A], C[A]) = underlying.partition(p)

  def splitAt(n: Int): (C[A], C[A]) = underlying.splitAt(n)

  def take(n: Int): C[A] = underlying.take(n)

  def takeRight(n: Int): C[A] = underlying.takeRight(n)

  def takeWhile(p: A => Boolean): C[A] = underlying.takeWhile(p)

  def span(p: A => Boolean): (C[A], C[A]) = underlying.span(p)

  def drop(n: Int): C[A] = underlying.drop(n)

  def dropRight(n: Int): C[A] = underlying.dropRight(n)

  def dropWhile(p: A => Boolean): C[A] = underlying.dropWhile(p)

  def grouped(size: Int): Iterator[C[A]] = underlying.grouped(size)

  def sliding(size: Int): Iterator[C[A]] = underlying.sliding(size)

  def sliding(size: Int, step: Int): Iterator[C[A]] = underlying.sliding(size, step)

  def tail: C[A] = underlying.tail

  def init: C[A] = underlying.init

  def slice(from: Int, until: Int): C[A] = underlying.slice(from, until)

  def groupBy[K](f: A => K): Map[K, C[A]] = underlying.groupBy(f)

  def groupMap[K, B](key: A => K)(f: A => B): Map[K, C[B]] = underlying.groupMap(key)(f)

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

  def zip[B](that: NEC[B]): NEC[(A, B)] = constructor(underlying zip unwrap(that))

  def zip[B](that: IterableOnce[B]): C[(A, B)] = underlying.zip(that)

  def zipWithIndex: NEC[(A, Int)] = constructor(underlying.zipWithIndex)

  def zipAll[A1 >: A, B](
    that: Iterable[B],
    thisElem: A1,
    thatElem: B,
  ): NEC[(A1, B)] =
    constructor(underlying.zipAll(that, thisElem, thatElem))

  def unzip[A1, A2](implicit asPair: A <:< (A1, A2)): (NEC[A1], NEC[A2]) = underlying.unzip match {
    case (l, r) => (constructor(l), constructor(r))
  }

  def unzip3[A1, A2, A3](implicit asTriple: A <:< (A1, A2, A3)): (NEC[A1], NEC[A2], NEC[A3]) = underlying.unzip3 match {
    case (l, c, r) => (constructor(l), constructor(c), constructor(r))
  }

  def tails: Iterator[C[A]] = underlying.tails

  def inits: Iterator[C[A]] = underlying.inits

  def tapEach[U](f: A => U): NEC[A] = constructor(underlying.tapEach(f))

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

  def toArray[B >: A](implicit evidence$2: ClassTag[B]): Array[B] = underlying.toArray[B]

}

trait NonEmptyIterableCompanion[C[X] <: IterableOps[X, C, C[X]], NEC[_]] {

  protected[collection] def className: String
  protected[collection] def constructor[A](ca: C[A]): NEC[A]
  protected def newUnderlyingBuilder[A]: mutable.Builder[A, C[A]]

  def one[A](head: A): NEC[A]

  def fromHeadTail[A](head: A, tail: Iterable[A]): NEC[A] = {
    val builder = newUnderlyingBuilder[A].addOne(head)
    builder.addAll(tail)
    constructor(builder.result())
  }

  def of[A](head: A, tail: A*): NEC[A] = fromHeadTail(head, tail)

  protected def fromUnderlying[A](underlying: C[A]): Option[NEC[A]] =
    if (underlying.isEmpty) None else Some(constructor(underlying))

  protected def fromUnderlyingUnsafe[A](underlying: C[A]): NEC[A] =
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

}
