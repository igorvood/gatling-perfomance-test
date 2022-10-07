package ru.vtb.uasp.streaming.mdm.enrichment.itest.dao

import ru.vtb.uasp.common.dto.UaspDto
import ru.vtb.uasp.streaming.mdm.enrichment.itest.common.FooCounter

import java.text.SimpleDateFormat
import java.util.Calendar

object DataGeneratorDao {


  def generateMsgFields(id: Int): Map[String, Any] = {
    val random = new scala.util.Random
    val fooCounter = new FooCounter(id)
    val USER_START_ID_NUMBER = (Math.random() * 100000).toInt * 10000 + fooCounter.inc() * random.nextInt(1000)

    Map(
      "transaction_datetime" -> new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(Calendar.getInstance().getTime),
      "processing_datetime" -> new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(Calendar.getInstance().getTime),
      "processing_effectivedate" -> new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime),
      "op_id" -> random.nextInt(999999),
      "audit_ref_authcode" -> random.nextInt(999999),
      "audit_ref_rrn" -> random.alphanumeric.take(12).mkString.toUpperCase,
      "audit_ref_srn" -> random.alphanumeric.take(12).mkString.toUpperCase,
      "local_user_id" -> (fooCounter.inc() + USER_START_ID_NUMBER).toString,
      "balance_change" -> random.nextInt(9999)
    )
  }

  def generateWay4(userIdLocal: String, idMsg: String): UaspDto = {
    val random = new scala.util.Random
    UaspDto(
      id = userIdLocal,
      dataInt = Map(),
      dataLong = Map(
      ),
      dataFloat = Map(),
      dataDouble = Map(),
      dataDecimal = Map(),
      dataString = Map(
        "tcmt_account_num" -> userIdLocal,
      ),
      dataBoolean = Map(),
      uuid = java.util.UUID.randomUUID().toString,
      process_timestamp = Calendar.getInstance().getTimeInMillis
    )
  }


  def generateCrossLinkMdm(userIdLocal: String, idGloabal: String): UaspDto = {
    UaspDto(
      userIdLocal,
      Map("period" -> 12),
      Map(),
      Map(),
      Map(),
      Map(
        "product_rate" -> BigDecimal(1)
      ),
      Map(
        "product_nm" -> "product_nm",
        /*        "mask_card_number" -> "MASK_CARD_NUMBER",
                "customer_id" -> "customer_id",
                "source_system_cd" -> "source_system_cd",
                "pos_flg" -> "pos_flg",
                "account_num" -> "account_num",
                "is_virtual_card_flg" -> "is_virtual_card_flg",
                "card_expiration_dt" -> "card_expiration_dt",
                "payment_system_desc" -> "payment_system_desc",
                "card_type_cd" -> "card_type_cd",
                "salary_serv_pack_flg" -> "salary_serv_pack_flg",
                "salary_project_flg" -> "salary_project_flg",
                "salary_account_scheme_flg" -> "salary_account_scheme_flg",
                "salary_card_type_flg" -> "salary_card_type_flg",
                "salary_card_type_flg" -> "salary_card_type_flg",
                "contract_card_type_cd" -> "contract_card_type_cd",
                "mdm_id" -> "mdm_id",*/
      ),
      Map(),
      java.util.UUID.randomUUID().toString,
      Calendar.getInstance().getTimeInMillis
    )
  }

  def generateMortgageMdm(idGloabal: String): UaspDto = {
    UaspDto(
      idGloabal,
      Map(),
      Map(),
      Map(),
      Map(),
      Map(),
      Map(
        "MASK_CARD_NUMBER" -> "MASK_CARD_NUMBER",
        "customer_id" -> "customer_id",
        "source_system_cd" -> "source_system_cd",
        "pos_flg" -> "pos_flg",
        "account_num" -> "account_num",
        "is_virtual_card_flg" -> "is_virtual_card_flg",
        "card_expiration_dt" -> "card_expiration_dt",
        "payment_system_desc" -> "payment_system_desc",
        "card_type_cd" -> "card_type_cd",
        "salary_serv_pack_flg" -> "salary_serv_pack_flg",
        "salary_project_flg" -> "salary_project_flg",
        "salary_account_scheme_flg" -> "salary_account_scheme_flg",
        "salary_card_type_flg" -> "salary_card_type_flg",
        "salary_card_type_flg" -> "salary_card_type_flg",
        "contract_card_type_cd" -> "contract_card_type_cd",
        "MASK_CARD_NUMBER" -> "MASK_CARD_NUMBER",

      ),
      Map("is_mortgage" -> (idGloabal.map(a => a.toInt).sum % 2 == 0)),
      java.util.UUID.randomUUID().toString,
      Calendar.getInstance().getTimeInMillis
    )
  }

}
