package au.id.tmm.utilities.collection

import au.id.tmm.utilities.collection.classes.SafeGroupBy

import scala.collection.BuildFrom

package object syntax extends SafeGroupBy.ToSafeGroupByOps {

  implicit def iteratorOps[A](iterator: Iterator[A]): IteratorOps[A] = new IteratorOps[A](iterator)

  implicit def seqOps[C[_], A](
    seq: C[A],
  )(implicit
    buildFrom: BuildFrom[C[A], A, C[A]],
    ev: C[A] <:< Seq[A],
  ): SeqOps[C, A] =
    new SeqOps[C, A](seq)

  implicit def iterableOps[C[_], A](
    iterable: C[A],
  )(implicit
    buildFrom: BuildFrom[C[A], A, C[A]],
    ev: C[A] <:< Iterable[A],
  ): IterableOps[C, A] =
    new IterableOps[C, A](iterable)

}
