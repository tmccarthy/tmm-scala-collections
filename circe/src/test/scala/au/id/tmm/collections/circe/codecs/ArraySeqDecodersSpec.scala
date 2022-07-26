package au.id.tmm.collections.circe.codecs

import au.id.tmm.utilities.testing.syntax._
import io.circe.syntax.EncoderOps
import io.circe.{Decoder, Json}
import munit.FunSuite

import scala.collection.immutable.ArraySeq
import scala.reflect.ClassTag

class ArraySeqDecodersSpec extends FunSuite {

  private def assertSpecialisedAs[T : ClassTag](nonEmptyArraySeq: ArraySeq[_]): Unit =
    assertEquals(
      nonEmptyArraySeq.getClass.asInstanceOf[Class[Any]],
      implicitly[ClassTag[T]].runtimeClass.asInstanceOf[Class[Any]],
    )

  test("the ArraySeq decoder should return a ref ArraySeq if its usage is untagged") {
    val array = Json.arr(
      1.asJson,
    )

    def decodeForceUntagged[A : Decoder](json: Json): Decoder.Result[ArraySeq[A]] = json.as[ArraySeq[A]]

    assertSpecialisedAs[ArraySeq.ofRef[_]](decodeForceUntagged[Int](array).get)
  }

  test("the ArraySeq decoder should return a ref ArraySeq for a tagged reference type") {
    val array = Json.arr(
      "array".asJson,
    )

    assertSpecialisedAs[ArraySeq.ofRef[_]](array.as[ArraySeq[String]].get)
  }

  test("the ArraySeq decoder should return a specialised ArraySeq for a tagged primative type") {
    val array = Json.arr(
      1.asJson,
    )

    assertSpecialisedAs[ArraySeq.ofInt](array.as[ArraySeq[Int]].get)
  }

}
