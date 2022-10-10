package ru.vood.gatling.test.utils

import play.api.libs.json.{Json, OWrites}
import ru.vood.gatling.test.utils.dto.{Identity, KafkaDto, KafkaStrDto}

object JsonConvertOutService extends Serializable   {

  def serializeToBytes[T<:Identity](value: T)(implicit oWrites:  OWrites[T]): KafkaDto = {
    val kafkaStrDto = serializeToStr(value)
    KafkaDto(kafkaStrDto.id.getBytes(), kafkaStrDto.value.getBytes())
  }

  def serializeToStr[T<:Identity](value: T)(implicit oWrites:  OWrites[T]): KafkaStrDto = KafkaStrDto(value.id, Json.stringify(Json.toJsObject(value)))

}
