package au.id.tmm.utilities.collection.syntax

import scala.collection.{BuildFrom, mutable}

final class IterableOps[C[_], A] private[syntax] (
  iterable: C[A],
)(implicit
  buildFrom: BuildFrom[C[A], A, C[A]],
  ev: C[A] <:< Iterable[A],
) {

  def atMostOneOr[E](error: => E): Either[E, Option[A]] = {
    if (iterable.isEmpty) return Right(None)

    val first2Elements = iterable.take(2)

    if (first2Elements.size == 1)
      Right(Some(first2Elements.head))
    else
      Left(error)
  }

  def atMostOne: Either[Exception, Option[A]] =
    atMostOneOr(new Exception(s"Expected at most one element. $describeIterable"))

  def onlyElement: Option[A] = {
    val first2Elements = iterable.take(2)

    if (first2Elements.size == 1)
      Some(first2Elements.head)
    else
      None
  }

  def onlyElementOr[E](error: => E): Either[E, A] =
    onlyElement.toRight(error)

  def onlyElementOrException: Either[Exception, A] =
    onlyElementOr(new Exception(s"Expected exactly one element. $describeIterable"))

  def emptyOr[E](error: => E): Either[E, Unit] =
    if (iterable.isEmpty) Right(()) else Left(error)

  def emptyOrException: Either[Exception, Unit] =
    emptyOr(new Exception(s"Expected empty iterable. $describeIterable"))

  private def describeIterable: String = s"Iterable was $iterable"

  def countOccurrences: Map[A, Int] = {
    val builder: mutable.Map[A, Int] = mutable.Map[A, Int]()

    iterable.foreach { a =>
      builder.updateWith(a) {
        case Some(previousCount) => Some(previousCount + 1)
        case None                => Some(1)
      }
    }

    builder.toMap
  }

  def groupByKey[K, V](implicit evA: A <:< (K, V)): Map[K, C[V]] =
    iterable
      .groupMap[K, V](
        key = {
          case (k: K @unchecked, v) => k
        },
      )(
        f = {
          case (k, v: V @unchecked) => v
        },
      )
      .asInstanceOf[Map[K, C[V]]]

}
