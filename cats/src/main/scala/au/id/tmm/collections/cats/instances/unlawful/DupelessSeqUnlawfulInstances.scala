package au.id.tmm.collections.cats.instances.unlawful

import au.id.tmm.collections.DupelessSeq
import cats.Monad

import scala.collection.mutable

trait DupelessSeqUnlawfulInstances {

  implicit val catsUnlawfulInstancesForDupelessSeq: Monad[DupelessSeq] = new Monad[DupelessSeq] {
    override def flatMap[A, B](fa: DupelessSeq[A])(f: A => DupelessSeq[B]): DupelessSeq[B] =
      fa.flatMap(f)

    override def tailRecM[A, B](a: A)(f: A => DupelessSeq[Either[A, B]]): DupelessSeq[B] = {
      val resultBuilder: DupelessSeq.DupelessSeqBuilder[B] = DupelessSeq.newBuilder[B]
      val aQueue: mutable.Queue[A]                         = mutable.Queue(a)

      while (aQueue.nonEmpty) {
        f(aQueue.dequeue()) foreach {
          case Right(b) => resultBuilder.addOne(b)
          case Left(a)  => aQueue.append(a)
        }
      }

      resultBuilder.result()
    }

    override def pure[A](x: A): DupelessSeq[A] = DupelessSeq(x)
  }

}
