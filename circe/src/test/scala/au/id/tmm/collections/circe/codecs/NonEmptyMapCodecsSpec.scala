package au.id.tmm.collections.circe.codecs

import au.id.tmm.collections.NonEmptyMap
import au.id.tmm.collections.circe.codecs.all._
import au.id.tmm.utilities.testing.syntax._
import io.circe.syntax.EncoderOps
import io.circe.{Json, KeyEncoder}
import munit.FunSuite

class NonEmptyMapCodecsSpec extends FunSuite {

  test("the non empty map encoder should encode a non-empty map with ordered keys") {
    val map = NonEmptyMap.of("a" -> 1, "b" -> 2)

    val expectedJson = Json.obj(
      "a" -> 1.asJson,
      "b" -> 2.asJson,
    )

    assertEquals(map.asJson, expectedJson)
  }

  test("the non empty map encoder can encode a non-empty map with unordered keys") {
    // We do this to create an "unordered" key
    final case class StringWrapper(asString: String)
    implicit val encoder: KeyEncoder[StringWrapper] = KeyEncoder[String].contramap(_.asString)

    val map = NonEmptyMap.of(
      StringWrapper("a") -> 1,
      StringWrapper("b") -> 2,
      StringWrapper("c") -> 3,
      StringWrapper("d") -> 4,
      StringWrapper("e") -> 5,
    )

    val validJsonFields =
      List(
        "a" -> 1.asJson,
        "b" -> 2.asJson,
        "c" -> 3.asJson,
        "d" -> 4.asJson,
        "e" -> 5.asJson,
      )

    val validJsons: Set[Json] =
      validJsonFields.permutations.map(fieldsPermutation => Json.obj(fieldsPermutation: _*)).toSet

    assert(validJsons contains map.asJson)
  }

  test("the non-empty map decoder can decode a non-empty json object") {
    val json = Json.obj(
      "a" -> 1.asJson,
      "b" -> 2.asJson,
    )

    val expectedMap = NonEmptyMap.of("a" -> 1, "b" -> 2)

    assertEquals(json.as[NonEmptyMap[String, Int]], Right(expectedMap))
  }

  test("the non-empty map decoder can not decode an empty json object") {
    assertEquals(
      Json.obj().as[NonEmptyMap[String, Int]].leftGet.message,
      "Empty array cannot be decoded to NonEmptyMap",
    )
  }

}
