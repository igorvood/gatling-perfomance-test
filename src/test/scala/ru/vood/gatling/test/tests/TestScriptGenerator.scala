package ru.vood.gatling.test.tests

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import ru.vood.gatling.test.dao.DataGeneratorDao.{generateCrossLinkMdm, generateWay4}
import ru.vood.gatling.test.dto.SomeDto
import ru.vood.gatling.test.scenario.{CountId, SendScenarioBuilder}
import ru.vood.gatling.test.utils.IdsListGenerator.config.{kafkaInMdmCrossLinkMessagesConf, kafkaInWay4MessagesConf}
import ru.vood.gatling.test.utils.IdsListGenerator.{COUNT_TRANSACTION, COUNT_MSG}

class TestScriptGenerator extends Simulation {
  implicit val countId = CountId(COUNT_MSG/100000)

  implicit val convertToBytes: (String, SomeDto) => (Array[Byte], Array[Byte]) = {
    (id, data) => (id.getBytes(), data.serializeToAvro._2)
  }

  val COUNT_MESSAGES: Int = COUNT_MSG * COUNT_TRANSACTION
  val CASE_NUMBER: Int = sys.env.getOrElse("CASE_NUMBER", "1").toInt

  if (CASE_NUMBER == 1) {
    // Передач холодного и горячего
    val sendMdmCrossLinksScenario = SendScenarioBuilder("kafkaInMdmCrossLinkMessages", generateCrossLinkMdm)
    val sendWay4Scenario = SendScenarioBuilder("kafkaInWay4MessagesConf", generateWay4)

    setUp(
      sendMdmCrossLinksScenario.inject(atOnceUsers(COUNT_MSG)).protocols(kafkaInMdmCrossLinkMessagesConf).andThen(
        sendWay4Scenario.inject(atOnceUsers(COUNT_MSG)).protocols(kafkaInWay4MessagesConf))
    )
  }
  else if (CASE_NUMBER == 2) {

    val sendMdmCrossLinksScenario = SendScenarioBuilder("kafkaInMdmCrossLinkMessages", generateCrossLinkMdm)

    setUp(

      sendMdmCrossLinksScenario.inject(atOnceUsers(COUNT_MSG)).protocols(kafkaInMdmCrossLinkMessagesConf),
    )

  }

}
