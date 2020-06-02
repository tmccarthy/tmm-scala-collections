package au.id.tmm.collections.cats.instances.unlawful

import au.id.tmm.collections.{DupelessSeq, NonEmptyDupelessSeq}
import cats.Monad

trait NonEmptyDupelessSeqUnlawfulInstances {
  import au.id.tmm.collections.cats.instances.unlawful.dupelessSeq._

  implicit val catsUnlawfulInstancesForNonEmptyDupelessSeq: Monad[NonEmptyDupelessSeq] =
    new Monad[NonEmptyDupelessSeq] {
      override def flatMap[A, B](fa: NonEmptyDupelessSeq[A])(f: A => NonEmptyDupelessSeq[B]): NonEmptyDupelessSeq[B] =
        fa.flatMap(f)

      override def tailRecM[A, B](a: A)(f: A => NonEmptyDupelessSeq[Either[A, B]]): NonEmptyDupelessSeq[B] =
        NonEmptyDupelessSeq.fromIterableUnsafe(Monad[DupelessSeq].tailRecM(a)(f.andThen(_.underlying)))

      override def pure[A](x: A): NonEmptyDupelessSeq[A] = NonEmptyDupelessSeq.one(x)
    }
}
