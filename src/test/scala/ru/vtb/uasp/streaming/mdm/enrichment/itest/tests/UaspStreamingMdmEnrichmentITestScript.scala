package ru.vtb.uasp.streaming.mdm.enrichment.itest.tests

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import ru.vtb.uasp.streaming.mdm.enrichment.itest.scenario._
import ru.vtb.uasp.streaming.mdm.enrichment.itest.utils.IdsListGenerator.config.kafkaInMdmCrossLinkMessagesConf
import ru.vtb.uasp.streaming.mdm.enrichment.itest.utils.IdsListGenerator.{COUNT_TRANSACTION, COUNT_USERS, config}

class UaspStreamingMdmEnrichmentITestScript extends Simulation {
  val COUNT_MESSAGES: Int = COUNT_USERS * COUNT_TRANSACTION
  val CASE_NUMBER: Int = sys.env.getOrElse("CASE_NUMBER", "1").toInt

  //     setUp(new SendRateScenarioBuilder(config).getScenario.inject(atOnceUsers(config.dateDiapason)).protocols(config.kafkaInRateMessagesConf))

  if (CASE_NUMBER == 1) {
    // Передачи кросс ссылок мдм (наполняем состояние кросс ссылками перед стартом обогащения)
    val sendMdmCrossLinksScenarioBuilder: SendMdmCrossLinksScenarioBuilder = new SendMdmCrossLinksScenarioBuilder(COUNT_USERS, config)
    val sendMdmCrossLinksScenario = sendMdmCrossLinksScenarioBuilder.getSendMdmCrossLinksScenario

    // Отправка в Way4 сообщение дожидается завершения передачи кросс ссылок мдм
    val sendWay4ScenarioBuilder: SendWay4ScenarioBuilder = new SendWay4ScenarioBuilder(COUNT_USERS, COUNT_TRANSACTION, config)
    val sendWay4Scenario = sendWay4ScenarioBuilder.getSendWay4Scenario


    setUp(
      //      sendRateScenarioBuilder.getScenario.inject(atOnceUsers(config.dateDiapason)).protocols(config.kafkaInRateMessagesConf),

      //      sendMortgageScenario.inject(atOnceUsers(COUNT_USERS)).protocols(kafkaInMortgageMessagesConf),
      sendMdmCrossLinksScenario.inject(atOnceUsers(COUNT_USERS)).protocols(kafkaInMdmCrossLinkMessagesConf),
      //      sendWay4Scenario.inject(atOnceUsers(COUNT_USERS)).protocols(kafkaInWay4MessagesConf)
    )
  }
  else if (CASE_NUMBER == 2) {

    val sendMdmCrossLinksScenarioBuilder: SendMdmCrossLinksScenarioBuilder = new SendMdmCrossLinksScenarioBuilder(COUNT_USERS, config)
    val sendMdmCrossLinksScenario = sendMdmCrossLinksScenarioBuilder.getSendMdmCrossLinksScenario

    // Отправка в Way4 сообщение дожидается завершения передачи кросс ссылок мдм
    val sendWay4ScenarioBuilder: SendWay4ScenarioBuilder = new SendWay4ScenarioBuilder(COUNT_USERS, COUNT_TRANSACTION, config)
    val sendWay4Scenario = sendWay4ScenarioBuilder.getSendWay4Scenario

    setUp(

      //      sendRateScenarioBuilder.getScenario.inject(atOnceUsers(config.dateDiapason)).protocols(config.kafkaInRateMessagesConf),

      //      sendMortgageScenario.inject(atOnceUsers(COUNT_USERS)).protocols(kafkaInMortgageMessagesConf),
      sendMdmCrossLinksScenario.inject(atOnceUsers(COUNT_USERS)).protocols(kafkaInMdmCrossLinkMessagesConf),
      //      sendWay4Scenario.inject(atOnceUsers(COUNT_USERS)).protocols(kafkaInWay4MessagesConf)
    )

  }

}
