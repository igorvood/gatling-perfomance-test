package ru.vood.gatling.test.entity

import com.github.mnogu.gatling.kafka.Predef.kafka
import com.github.mnogu.gatling.kafka.protocol.KafkaProtocol
import io.gatling.core.Predef.configuration
import ru.vood.gatling.test.utils.KafkaPropertiesUtil

case class Config(
                   topicInWay4: String,
                   topicInCrossLinkMdm: String,
                   topicInMortgage: String,
                   topicOutMortgage: String,
                   topicOutCrossLinkMdmStatus: String,
                   topicOutEnrichmentWay4: String,
                   bootstrapServers: String,
                   sslTruststoreLocation: String,
                   sslTruststorePassword: String,
                   sslKeystoreLocation: String,
                   sslKeystorePassword: String,
                   sslKeyPassword: String,
                   enablePrefix: String,
                   prefix: String,
                 ) {


  val kafkaInMdmCrossLinkMessagesConf: KafkaProtocol = getKafkaProtocol(topicInCrossLinkMdm)

  val kafkaInWay4MessagesConf: KafkaProtocol = getKafkaProtocol(topicInWay4)


  private def getKafkaProtocol(topicName: String): KafkaProtocol = kafka.topic(topicName)
    .properties(KafkaPropertiesUtil.getProducerKafkaProperties(this))

}
