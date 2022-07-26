package au.id.tmm.collections.cats.instances

import cats.kernel.Eq
import org.scalacheck.{Arbitrary, Cogen, Shrink}

/**
  * A wrapper around `Int` for which there is an `Eq` implementation, but no `Hash` implementation. This can be used to
  * test low-priority `Eq` instances which will not be resolved for classes like `Int` which also have a `Hash`.
  */
final case class EqOnlyInt(asInt: Int) extends AnyVal

object EqOnlyInt {
  implicit val eq: Eq[EqOnlyInt]               = _.asInt == _.asInt
  implicit val arbitrary: Arbitrary[EqOnlyInt] = Arbitrary(Arbitrary.arbitrary[Int].map(EqOnlyInt.apply))
  implicit val cogen: Cogen[EqOnlyInt]         = Cogen[Int].contramap(_.asInt)
  implicit val shrink: Shrink[EqOnlyInt] =
    Shrink.withLazyList(x => Shrink.shrink(x.asInt).to(LazyList).map(EqOnlyInt.apply))
}
