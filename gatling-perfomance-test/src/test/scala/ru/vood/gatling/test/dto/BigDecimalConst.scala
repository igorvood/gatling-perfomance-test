package ru.vood.gatling.test.dto

import com.sksamuel.avro4s.ScalePrecision

object BigDecimalConst {
  val SCALE: Int = 5
  val PRECISION: Int = 23

  implicit val sp: ScalePrecision = ScalePrecision(SCALE, PRECISION)
}
