package au.id.tmm.utilities.collection.syntax

import scala.collection.BuildFrom

final class SeqOps[C[_], A] private[syntax] (
  seq: C[A],
)(implicit
  buildFrom: BuildFrom[C[A], A, C[A]],
  ev: C[A] <:< Seq[A],
) {
  def everyNth(n: Int): C[A] =
    buildFrom.fromSpecific(seq)(Range(0, seq.length, n).iterator.map(i => seq.apply(i)))
}
