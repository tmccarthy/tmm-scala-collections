package au.id.tmm.collections.circe.codecs

import au.id.tmm.collections.cats.instances.all._
import au.id.tmm.collections.circe.codecs.all._
import au.id.tmm.collections.scalacheck.all._
import au.id.tmm.collections.{DupelessSeq, NonEmptyArraySeq, NonEmptyDupelessSeq, NonEmptySet}
import cats.instances.arraySeq._
import io.circe.testing.CodecTests
import io.circe.testing.instances._
import munit.DisciplineSuite

import scala.collection.immutable.ArraySeq

class CirceCodecLawsSuite extends DisciplineSuite {

  checkAll("ArraySeq codecs", CodecTests[ArraySeq[Int]].codec)
  checkAll("NonEmptyArraySeq codecs", CodecTests[NonEmptyArraySeq[Int]].codec)

  checkAll("DupelessSeq codecs", CodecTests[DupelessSeq[Int]].codec)
  checkAll("NonEmptyDupelessSeq codecs", CodecTests[NonEmptyDupelessSeq[Int]].codec)

  checkAll("NonEmptySet codecs", CodecTests[NonEmptySet[Int]].codec)

}
