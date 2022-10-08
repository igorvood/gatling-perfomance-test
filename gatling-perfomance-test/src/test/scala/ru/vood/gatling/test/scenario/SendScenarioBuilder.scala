package ru.vood.gatling.test.scenario

import com.github.mnogu.gatling.kafka.Predef._
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import ru.vood.gatling.test.common.Finisheable
import ru.vood.gatling.test.scenario.SendScenarioBuilder.{byteIDName, bytesDtoName}

import scala.math.abs

class SendScenarioBuilder[T](
                              private val senderName: String = "kafkaInMdmCrossLinkMessages",
                              private val cntIds: Int,
                              private val generateDto: String => T,
                              private val convertToBytes: (String, T) => (Array[Byte], Array[Byte])

                            ) extends Finisheable {

  assert(cntIds>0, s"cntIds must be more than 0 current value is $cntIds")

  def isFinished: Boolean = true

  def sendScenario: ScenarioBuilder = {

    scenario(senderName)
      .exec(session => {
        val localUserId = abs(java.util.UUID.randomUUID().toString.hashCode % cntIds).toString

        val localCrossLinkUaspDto = generateDto(localUserId)

        val tuple = convertToBytes(localUserId, localCrossLinkUaspDto)

        session
          .set(byteIDName, tuple._1)
          .set(bytesDtoName, tuple._2)

      })
      .exec(kafka(senderName).send[Array[Byte], Array[Byte]]("${" + byteIDName + "}", "${" + bytesDtoName + "}"))
  }

}

object SendScenarioBuilder {

  private val byteIDName = "byteIDName"
  private val bytesDtoName = "bytesDtoName"


  def apply[T](senderName: String = "kafkaInMdmCrossLinkMessages", generateDto: String => T)(implicit cntIds: CountId, convertToBytes: (String, T) => (Array[Byte], Array[Byte])) =

    new SendScenarioBuilder(senderName, cntIds.cnt, generateDto, convertToBytes).sendScenario
}
