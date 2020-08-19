package au.id.tmm.collections.syntax

import scala.collection.mutable

private[syntax] trait UnzipOps[C[_], A] { self: IterableOps[C, A] =>

  def unzip4[A1, A2, A3, A4](
    implicit
    evA: A <:< (A1, A2, A3, A4),
  ): (C[A1], C[A2], C[A3], C[A4]) = {
    val c1 = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A1, C[A1]]]
    val c2 = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A2, C[A2]]]
    val c3 = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A3, C[A3]]]
    val c4 = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A4, C[A4]]]

    self.iterable.foreach { tuple =>
      c1.addOne(tuple._1)
      c2.addOne(tuple._2)
      c3.addOne(tuple._3)
      c4.addOne(tuple._4)
    }

    (
      c1.result(),
      c2.result(),
      c3.result(),
      c4.result(),
    )
  }

  def unzip5[A1, A2, A3, A4, A5](
    implicit
    evA: A <:< (A1, A2, A3, A4, A5),
  ): (C[A1], C[A2], C[A3], C[A4], C[A5]) = {
    val c1 = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A1, C[A1]]]
    val c2 = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A2, C[A2]]]
    val c3 = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A3, C[A3]]]
    val c4 = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A4, C[A4]]]
    val c5 = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A5, C[A5]]]

    self.iterable.foreach { tuple =>
      c1.addOne(tuple._1)
      c2.addOne(tuple._2)
      c3.addOne(tuple._3)
      c4.addOne(tuple._4)
      c5.addOne(tuple._5)
    }

    (
      c1.result(),
      c2.result(),
      c3.result(),
      c4.result(),
      c5.result(),
    )
  }

  def unzip6[A1, A2, A3, A4, A5, A6](
    implicit
    evA: A <:< (A1, A2, A3, A4, A5, A6),
  ): (C[A1], C[A2], C[A3], C[A4], C[A5], C[A6]) = {
    val c1 = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A1, C[A1]]]
    val c2 = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A2, C[A2]]]
    val c3 = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A3, C[A3]]]
    val c4 = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A4, C[A4]]]
    val c5 = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A5, C[A5]]]
    val c6 = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A6, C[A6]]]

    self.iterable.foreach { tuple =>
      c1.addOne(tuple._1)
      c2.addOne(tuple._2)
      c3.addOne(tuple._3)
      c4.addOne(tuple._4)
      c5.addOne(tuple._5)
      c6.addOne(tuple._6)
    }

    (
      c1.result(),
      c2.result(),
      c3.result(),
      c4.result(),
      c5.result(),
      c6.result(),
    )
  }

  def unzip7[A1, A2, A3, A4, A5, A6, A7](
    implicit
    evA: A <:< (A1, A2, A3, A4, A5, A6, A7),
  ): (C[A1], C[A2], C[A3], C[A4], C[A5], C[A6], C[A7]) = {
    val c1 = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A1, C[A1]]]
    val c2 = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A2, C[A2]]]
    val c3 = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A3, C[A3]]]
    val c4 = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A4, C[A4]]]
    val c5 = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A5, C[A5]]]
    val c6 = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A6, C[A6]]]
    val c7 = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A7, C[A7]]]

    self.iterable.foreach { tuple =>
      c1.addOne(tuple._1)
      c2.addOne(tuple._2)
      c3.addOne(tuple._3)
      c4.addOne(tuple._4)
      c5.addOne(tuple._5)
      c6.addOne(tuple._6)
      c7.addOne(tuple._7)
    }

    (
      c1.result(),
      c2.result(),
      c3.result(),
      c4.result(),
      c5.result(),
      c6.result(),
      c7.result(),
    )
  }

  def unzip8[A1, A2, A3, A4, A5, A6, A7, A8](
    implicit
    evA: A <:< (A1, A2, A3, A4, A5, A6, A7, A8),
  ): (C[A1], C[A2], C[A3], C[A4], C[A5], C[A6], C[A7], C[A8]) = {
    val c1 = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A1, C[A1]]]
    val c2 = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A2, C[A2]]]
    val c3 = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A3, C[A3]]]
    val c4 = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A4, C[A4]]]
    val c5 = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A5, C[A5]]]
    val c6 = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A6, C[A6]]]
    val c7 = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A7, C[A7]]]
    val c8 = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A8, C[A8]]]

    self.iterable.foreach { tuple =>
      c1.addOne(tuple._1)
      c2.addOne(tuple._2)
      c3.addOne(tuple._3)
      c4.addOne(tuple._4)
      c5.addOne(tuple._5)
      c6.addOne(tuple._6)
      c7.addOne(tuple._7)
      c8.addOne(tuple._8)
    }

    (
      c1.result(),
      c2.result(),
      c3.result(),
      c4.result(),
      c5.result(),
      c6.result(),
      c7.result(),
      c8.result(),
    )
  }

  def unzip9[A1, A2, A3, A4, A5, A6, A7, A8, A9](
    implicit
    evA: A <:< (A1, A2, A3, A4, A5, A6, A7, A8, A9),
  ): (C[A1], C[A2], C[A3], C[A4], C[A5], C[A6], C[A7], C[A8], C[A9]) = {
    val c1 = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A1, C[A1]]]
    val c2 = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A2, C[A2]]]
    val c3 = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A3, C[A3]]]
    val c4 = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A4, C[A4]]]
    val c5 = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A5, C[A5]]]
    val c6 = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A6, C[A6]]]
    val c7 = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A7, C[A7]]]
    val c8 = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A8, C[A8]]]
    val c9 = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A9, C[A9]]]

    self.iterable.foreach { tuple =>
      c1.addOne(tuple._1)
      c2.addOne(tuple._2)
      c3.addOne(tuple._3)
      c4.addOne(tuple._4)
      c5.addOne(tuple._5)
      c6.addOne(tuple._6)
      c7.addOne(tuple._7)
      c8.addOne(tuple._8)
      c9.addOne(tuple._9)
    }

    (
      c1.result(),
      c2.result(),
      c3.result(),
      c4.result(),
      c5.result(),
      c6.result(),
      c7.result(),
      c8.result(),
      c9.result(),
    )
  }

  def unzip10[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10](
    implicit
    evA: A <:< (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10),
  ): (C[A1], C[A2], C[A3], C[A4], C[A5], C[A6], C[A7], C[A8], C[A9], C[A10]) = {
    val c1  = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A1, C[A1]]]
    val c2  = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A2, C[A2]]]
    val c3  = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A3, C[A3]]]
    val c4  = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A4, C[A4]]]
    val c5  = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A5, C[A5]]]
    val c6  = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A6, C[A6]]]
    val c7  = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A7, C[A7]]]
    val c8  = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A8, C[A8]]]
    val c9  = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A9, C[A9]]]
    val c10 = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A10, C[A10]]]

    self.iterable.foreach { tuple =>
      c1.addOne(tuple._1)
      c2.addOne(tuple._2)
      c3.addOne(tuple._3)
      c4.addOne(tuple._4)
      c5.addOne(tuple._5)
      c6.addOne(tuple._6)
      c7.addOne(tuple._7)
      c8.addOne(tuple._8)
      c9.addOne(tuple._9)
      c10.addOne(tuple._10)
    }

    (
      c1.result(),
      c2.result(),
      c3.result(),
      c4.result(),
      c5.result(),
      c6.result(),
      c7.result(),
      c8.result(),
      c9.result(),
      c10.result(),
    )
  }

  def unzip11[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11](
    implicit
    evA: A <:< (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11),
  ): (C[A1], C[A2], C[A3], C[A4], C[A5], C[A6], C[A7], C[A8], C[A9], C[A10], C[A11]) = {
    val c1  = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A1, C[A1]]]
    val c2  = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A2, C[A2]]]
    val c3  = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A3, C[A3]]]
    val c4  = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A4, C[A4]]]
    val c5  = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A5, C[A5]]]
    val c6  = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A6, C[A6]]]
    val c7  = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A7, C[A7]]]
    val c8  = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A8, C[A8]]]
    val c9  = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A9, C[A9]]]
    val c10 = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A10, C[A10]]]
    val c11 = self.buildFrom.newBuilder(self.iterable).asInstanceOf[mutable.Builder[A11, C[A11]]]

    self.iterable.foreach { tuple =>
      c1.addOne(tuple._1)
      c2.addOne(tuple._2)
      c3.addOne(tuple._3)
      c4.addOne(tuple._4)
      c5.addOne(tuple._5)
      c6.addOne(tuple._6)
      c7.addOne(tuple._7)
      c8.addOne(tuple._8)
      c9.addOne(tuple._9)
      c10.addOne(tuple._10)
      c11.addOne(tuple._11)
    }

    (
      c1.result(),
      c2.result(),
      c3.result(),
      c4.result(),
      c5.result(),
      c6.result(),
      c7.result(),
      c8.result(),
      c9.result(),
      c10.result(),
      c11.result(),
    )
  }

}
