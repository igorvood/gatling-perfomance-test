package ru.vtb.uasp.streaming.mdm.enrichment.itest.scenario

import com.github.mnogu.gatling.kafka.Predef._
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import ru.vtb.uasp.common.dto.UaspDto
import ru.vtb.uasp.streaming.mdm.enrichment.itest.common.Finisheable

import scala.math.abs

class SendScenarioBuilder(
                           private val senderName: String = "kafkaInMdmCrossLinkMessages",
                           private val cntIds: Int,
                           private val generateDto: String => UaspDto,
                           private val convertToBytes: (String, UaspDto) => (Array[Byte], Array[Byte])

                         ) extends Finisheable {


  private val byteIDName = "byteIDName"
  private val bytesDtoName = "bytesDtoName"

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


  def apply(senderName: String = "kafkaInMdmCrossLinkMessages", generateDto: String => UaspDto)(implicit cntIds: CountId, convertToBytes: (String, UaspDto) => (Array[Byte], Array[Byte])) =

    new SendScenarioBuilder(senderName, cntIds.cnt, generateDto, convertToBytes).sendScenario
}
