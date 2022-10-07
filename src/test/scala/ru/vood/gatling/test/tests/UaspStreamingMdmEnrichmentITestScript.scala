package ru.vood.gatling.test.tests

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import ru.vood.gatling.test.dao.DataGeneratorDao.{generateCrossLinkMdm, generateWay4}
import ru.vood.gatling.test.scenario.CommonObject.{encoderUaspDto, genericDatumWriterUaspDto}
import ru.vood.gatling.test.scenario.{CountId, SendScenarioBuilder}
import ru.vood.gatling.test.utils.IdsListGenerator.config.{kafkaInMdmCrossLinkMessagesConf, kafkaInWay4MessagesConf}
import ru.vood.gatling.test.utils.IdsListGenerator.{COUNT_TRANSACTION, COUNT_USERS}
import ru.vtb.uasp.common.dto.UaspDto
import ru.vtb.uasp.common.utils.avro.AvroSerializeUtil

class UaspStreamingMdmEnrichmentITestScript extends Simulation {
  implicit val countId = CountId(1000)

  implicit val convertToBytes: (String, UaspDto) => (Array[Byte], Array[Byte]) = {
    (id, data) => (id.getBytes(), AvroSerializeUtil.encode[UaspDto](data, encoderUaspDto, genericDatumWriterUaspDto))
  }

  val COUNT_MESSAGES: Int = COUNT_USERS * COUNT_TRANSACTION
  val CASE_NUMBER: Int = sys.env.getOrElse("CASE_NUMBER", "1").toInt

  if (CASE_NUMBER == 1) {
    // Передач холодного и горячего
    val sendMdmCrossLinksScenario = SendScenarioBuilder("kafkaInMdmCrossLinkMessages", generateCrossLinkMdm)
    val sendWay4Scenario = SendScenarioBuilder("kafkaInWay4MessagesConf", generateWay4)

    // Отправка в Way4 сообщение дожидается завершения передачи кросс ссылок мдм
    //    val sendWay4ScenarioBuilder: SendWay4ScenarioBuilder = new SendWay4ScenarioBuilder(COUNT_USERS, COUNT_TRANSACTION, config)
    //    val sendWay4Scenario = sendWay4ScenarioBuilder.getSendWay4Scenario


    setUp(
      sendMdmCrossLinksScenario.inject(atOnceUsers(COUNT_USERS)).protocols(kafkaInMdmCrossLinkMessagesConf),
      sendWay4Scenario.inject(atOnceUsers(COUNT_USERS)).protocols(kafkaInWay4MessagesConf)
    )
  }
  else if (CASE_NUMBER == 2) {

    val sendMdmCrossLinksScenario = SendScenarioBuilder("kafkaInMdmCrossLinkMessages", generateCrossLinkMdm)

    setUp(

      sendMdmCrossLinksScenario.inject(atOnceUsers(COUNT_USERS)).protocols(kafkaInMdmCrossLinkMessagesConf),
    )

  }

}
