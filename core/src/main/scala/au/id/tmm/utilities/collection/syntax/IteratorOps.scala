package au.id.tmm.utilities.collection.syntax

import scala.annotation.tailrec
import scala.collection.mutable

final class IteratorOps[+A] private[syntax] (iterator: Iterator[A]) {

  /**
    * Reads either the next `n` elements of the iterator, or to its end, whichever comes first. This method differs
    * from `scala.collection.Iterator.take`, in that it simply reads the elements from the underlying iterator,
    * leaving it free for continued use after the invocation.
    * @return the elements read from the iterator
    */
  def readAtMost(n: Int): Vector[A] = {
    val result: mutable.ArrayBuffer[A] = new mutable.ArrayBuffer(n)

    while (result.size < n && iterator.hasNext) {
      result += iterator.next()
    }

    result.toVector
  }

  /**
    * Reads until the given condition is satisfied, or until the end of the iterator. This method differs from
    * `scala.collection.Iterable.dropWhile`, in that it simply reads the elements from the underlying iterator,
    * leaving it free for continued use after the invocation.
    * @return the elements read before the condition is met or the iterator is exhausted
    */
  def readUntil(p: A => Boolean): Vector[A] = {
    val result: mutable.ArrayBuffer[A] = mutable.ArrayBuffer()

    @tailrec
    def readMore(): Unit =
      if (iterator.hasNext) {
        val element = iterator.next()

        result += element

        if (!p(element)) {
          readMore()
        }
      }

    readMore()

    result.toVector
  }

  /**
    * Reads from the iterator until either the given number of elements have been read, the given condition has been
    * satisfied, or the iterator is exhausted. The underlying iterator can continue to be used after this method
    * returns.
    * @param n the maximum number of elements to read from the iterator
    * @param p a condition if, when satisfied, stops the consumption of the iterator
    * @return the elements read from the iterator
    */
  def readAtMostUntil(n: Int, p: A => Boolean): Vector[A] = {
    val result: mutable.ArrayBuffer[A] = new mutable.ArrayBuffer[A](n)

    @tailrec
    def readMore(): Unit =
      if (iterator.hasNext) {
        val element = iterator.next()

        result += element

        if (!p(element) && result.size < n) {
          readMore()
        }
      }

    readMore()

    result.toVector
  }

}
