package ru.vood.gatling.test.utils

import ru.vood.gatling.test.entity.Config

import scala.util.Try

object IdsListGenerator {
  val sysEnv: Map[String, String] = sys.env
  val config: Config = ConfigUtil.getConf(sysEnv)

  val prefixFromProps: String = config.prefix

  val enablePrefix: Boolean = Try(config.enablePrefix.toBoolean).getOrElse(false)

  val prefix: String = returnPrefix(prefixFromProps, enablePrefix)

  val COUNT_USERS: Int = sys.env.getOrElse("COUNT_USERS", "20000").toInt
  val COUNT_TRANSACTION: Int = sys.env.getOrElse("COUNT_TRANSACTION", "1").toInt


  def returnPrefix(prefix: String, enablePrefix: Boolean): String = {
    if (enablePrefix) prefix else ""
  }
}
