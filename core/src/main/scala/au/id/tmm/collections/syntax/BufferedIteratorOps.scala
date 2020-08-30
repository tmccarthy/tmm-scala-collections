package au.id.tmm.collections.syntax

import scala.collection.{AbstractIterator, BufferedIterator}

final class BufferedIteratorOps[+A] private[syntax] (underlying: BufferedIterator[A]) {

  /**
    * Differs from `takeWhile` in that underlying iterator does not progress past the first element to fail the
    * predicate.
    */
  def takeUpTo(predicate: A => Boolean): Iterator[A] =
    new AbstractIterator[A] {
      override def hasNext: Boolean = underlying.hasNext && predicate(underlying.head)
      override def next(): A        = underlying.next()
      override def knownSize: Int =
        if (underlying.knownSize == 0 || !underlying.hasNext || !predicate(underlying.head)) 0 else super.knownSize
    }

}

object BufferedIteratorOps {

  trait ToBufferedIteratorOps {
    implicit def toBufferedIteratorOps[A](bufferedIterator: BufferedIterator[A]): BufferedIteratorOps[A] =
      new BufferedIteratorOps[A](bufferedIterator)
  }

}
