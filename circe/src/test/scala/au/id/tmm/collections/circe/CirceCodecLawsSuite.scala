package au.id.tmm.collections.circe

import au.id.tmm.collections.cats.instances.dupelessSeq._
import au.id.tmm.collections.cats.instances.nonEmptyArraySeq._
import au.id.tmm.collections.cats.instances.nonEmptyDupelessSeq._
import au.id.tmm.collections.cats.instances.nonEmptySet._
import au.id.tmm.collections.scalacheck.all._
import au.id.tmm.collections.{DupelessSeq, NonEmptyArraySeq, NonEmptyDupelessSeq, NonEmptySet}
import cats.instances.arraySeq._
import io.circe.testing.CodecTests
import io.circe.testing.instances._
import org.scalatest.funsuite.AnyFunSuiteLike
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks
import org.typelevel.discipline.scalatest.FunSuiteDiscipline

import scala.collection.immutable.ArraySeq

class CirceCodecLawsSuite extends AnyFunSuiteLike with ScalaCheckDrivenPropertyChecks with FunSuiteDiscipline {

  checkAll("ArraySeq codecs", CodecTests[ArraySeq[Int]].codec)
  checkAll("NonEmptyArraySeq codecs", CodecTests[NonEmptyArraySeq[Int]].codec)

  checkAll("DupelessSeq codecs", CodecTests[DupelessSeq[Int]].codec)
  checkAll("NonEmptyDupelessSeq codecs", CodecTests[NonEmptyDupelessSeq[Int]].codec)

  checkAll("NonEmptySet codecs", CodecTests[NonEmptySet[Int]].codec)

}
