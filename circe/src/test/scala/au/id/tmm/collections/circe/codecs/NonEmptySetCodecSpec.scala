package au.id.tmm.collections.circe.codecs

import au.id.tmm.collections.NonEmptySet
import au.id.tmm.collections.circe.codecs.all._
import au.id.tmm.utilities.testing.syntax._
import io.circe.syntax.EncoderOps
import io.circe.{Encoder, Json}
import munit.FunSuite

class NonEmptySetCodecSpec extends FunSuite {

  test("the non empty set encoder should encode a non-empty set of ordered elements") {
    assertEquals(NonEmptySet.of(1, 2).asJson, Json.arr(1.asJson, 2.asJson))
  }

  test("the non empty set encoder can encode a non-empty set of unordered elements") {
    // We do this to create an "unordered" int
    final case class IntWrapper(asInt: Int)
    implicit val encoder: Encoder[IntWrapper] = Encoder.encodeInt.contramap(_.asInt)

    val oneThroughTenSet: Set[Int] = Range.inclusive(1, 10).toSet

    val setToEncode: NonEmptySet[IntWrapper] = NonEmptySet.fromSetUnsafe(oneThroughTenSet.map(IntWrapper.apply))

    val encoded: Json = setToEncode.asJson

    val expectedJsonElementsInEncodedArray: Set[Json] = oneThroughTenSet.map(Json.fromInt)

    val actualJsonElementsInEncodedArray = encoded.asArray.get.toSet

    assertEquals(actualJsonElementsInEncodedArray, expectedJsonElementsInEncodedArray)
  }

  test("the non-empty set decoder can decode a non-empty json array") {
    assertEquals(Json.arr(1.asJson, 2.asJson).as[NonEmptySet[Int]].get, NonEmptySet.of(1, 2))
  }

  test("the non-empty set decoder can not decode an empty json array") {
    assertEquals(Json.arr().as[NonEmptySet[Int]].leftGet.message, "Empty array cannot be decoded to NonEmptySet")
  }

}
