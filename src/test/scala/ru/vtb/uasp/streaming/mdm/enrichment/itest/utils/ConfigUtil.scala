package ru.vtb.uasp.streaming.mdm.enrichment.itest.utils

import ru.vtb.uasp.streaming.mdm.enrichment.itest.entity.Config

object ConfigUtil {

  val groupId = 24

  def getConf(sysEnv: Map[String, String]): Config = {
    val config: Config = Config(
      topicInWay4 = sysEnv.getOrElse("topicInWay4", "dev_bevents__realtime__input_converter__prof__transactions__uaspdto"),
      topicInCrossLinkMdm = sysEnv.getOrElse("topicInCrossLinkMdm", "dev_rto_batch_ca_deposit_account_case_71_uaspdto"),
      //      topicInWay4 = sysEnv.getOrElse("topicInWay4", "A_CASE_71_HA_IN"),
      //      topicInCrossLinkMdm = sysEnv.getOrElse("topicInCrossLinkMdm", "A_CASE_71_CA_IN"),


      topicInMortgage = sysEnv.getOrElse("topicInMortgage", "dev_bevents_card_agreement_enrich_out_uaspdto"),
      topicOutMortgage = sysEnv.getOrElse("topicOutMortgage", "dev_bevents_card_agreement_enrich_out_uaspdto"),
      topicOutCrossLinkMdmStatus = sysEnv.getOrElse("topicOutCrossLinkMdmStatus", "dev_bevents_card_agreement_enrich_out_uaspdto"),
      topicOutEnrichmentWay4 = sysEnv.getOrElse("topicOutEnrichmentWay4", "dev_ivr__uasp_realtime__mdm_enrichment__uaspdto"),
      topicDLQ = sysEnv.getOrElse("topicDLQ", "dev_bevents_card_agreement_enrich_out_uaspdto"),
      topicInRate = sysEnv.getOrElse("topicInRate", "rate_in"),
      topicOutRate = sysEnv.getOrElse("topicInRate", "rate_out"),
      bootstrapServers = sysEnv.getOrElse("bootstrapServers", "d5uasp-kfc001lk.corp.dev.vtb:9092,d5uasp-kfc002lk.corp.dev.vtb:9092,d5uasp-kfc003lk.corp.dev.vtb:9092,d5uasp-kfc004lk.corp.dev.vtb:9092,d5uasp-kfc005lk.corp.dev.vtb:9092,d5uasp-kfc006lk.corp.dev.vtb:9092"),
      //      bootstrapServers = sysEnv.getOrElse("bootstrapServers", "172.19.128.77:9092,172.19.128.77:9091"),

      sslTruststoreLocation = sysEnv.getOrElse("sslTruststoreLocation", "C:\\Work\\SSL\\new_APD75-None-kafka-d5-client-uasp-truststore.pfx"),
      sslTruststorePassword = sysEnv.getOrElse("sslTruststorePassword", "p9HrxNIXS4ekSBKKD0Dh<zL7a2!"),
      sslKeystoreLocation = sysEnv.getOrElse("sslKeystoreLocation", "C:\\Work\\SSL\\new_APD75-None-kafka-d5-client-uasp.pfx"),
      sslKeystorePassword = sysEnv.getOrElse("sslKeystorePassword", "p9HrxNIXS4ekSBKKD0Dh<zL7a2!"),
      sslKeyPassword = sysEnv.getOrElse("sslKeyPassword", "p9HrxNIXS4ekSBKKD0Dh<zL7a2!"),

      /**
       * sysEnv.getOrElse("sslTruststoreLocation", "kafka-trust.pfx"),
       * sysEnv.getOrElse("sslTruststorePassword", ""),
       * sysEnv.getOrElse("sslKeystoreLocation", "APD00.13.01-USBP-kafka-cluster-uasp.pfx"),
       * sysEnv.getOrElse("sslKeystorePassword", ""),
       * sysEnv.getOrElse("sslKeyPassword", ""), */

      enablePrefix = sys.env.getOrElse("enablePrefix", "true"),
      prefix = sys.env.getOrElse("prefix", "EnItest!-"),
    )

    config
  }
}
