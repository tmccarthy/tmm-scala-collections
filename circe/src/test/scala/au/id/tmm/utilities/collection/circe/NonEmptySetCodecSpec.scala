package au.id.tmm.utilities.collection.circe

import au.id.tmm.utilities.collection.NonEmptySet
import au.id.tmm.utilities.testing.syntax._
import io.circe.syntax.EncoderOps
import io.circe.{Encoder, Json}
import org.scalatest.FlatSpec

class NonEmptySetCodecSpec extends FlatSpec {

  "the non empty set encoder" should "encode a non-empty set of ordered elements" in {
    assert(NonEmptySet.of(1, 2).asJson === Json.arr(1.asJson, 2.asJson))
  }

  it can "encode a non-empty set of unordered elements" in {
    // We do this to create an "unordered" int
    final case class IntWrapper(asInt: Int)
    implicit val encoder: Encoder[IntWrapper] = Encoder.encodeInt.contramap(_.asInt)

    val oneThroughTenSet: Set[Int] = Range.inclusive(1, 10).toSet

    val setToEncode: NonEmptySet[IntWrapper] = NonEmptySet.fromSetUnsafe(oneThroughTenSet.map(IntWrapper.apply))

    val encoded: Json = setToEncode.asJson

    val expectedJsonElementsInEncodedArray: Set[Json] = oneThroughTenSet.map(Json.fromInt)

    val actualJsonElementsInEncodedArray = encoded.asArray.get.toSet

    assert(actualJsonElementsInEncodedArray === expectedJsonElementsInEncodedArray)
  }

  "the non-empty set decoder" can "decode a non-empty json array" in {
    assert(Json.arr(1.asJson, 2.asJson).as[NonEmptySet[Int]].get === NonEmptySet.of(1, 2))
  }

  it can "not decode an empty json array" in {
    assert(Json.arr().as[NonEmptySet[Int]].leftGet.message === "Empty array cannot be decoded to NonEmptySet")
  }

}
