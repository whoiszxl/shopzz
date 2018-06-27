package com.whoiszxl.base.utils

import java.math.BigDecimal

/*
    金额 元与分 转换
 */
object YuanFenConverter {
    val CURRENCY_FEN_REGEX = "\\-?[0-9]+"

    /*
        分 转换为 元
     */
    fun changeF2Y(amount: Long?, defaultString: String): String {
        var result = defaultString
        try {
            result = changeF2Y(amount)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return result
    }

    /*
        分 转换为 元
     */
    fun changeF2Y(amount: Int?, defaultString: String): String {
        return changeF2Y(amount as Long, defaultString)
    }

    /*
        分 转换为 元
     */
    @Throws(Exception::class)
    fun changeF2Y(amount: Long?): String {
        if (null == amount) {
            return "0"
        }

        if (!amount.toString().matches(CURRENCY_FEN_REGEX.toRegex())) {
            throw Exception("Invalid format")
        }

        var flag = 0
        var amString = amount.toString()
        if (amString[0] == '-') {
            flag = 1
            amString = amString.substring(1)
        }
        val result = StringBuffer()
        if (amString.length == 1) {
            result.append("0.0").append(amString)
        } else if (amString.length == 2) {
            result.append("0.").append(amString)
        } else {
            val intString = amString.substring(0, amString.length - 2)
            for (i in 1..intString.length) {
                if ((i - 1) % 3 == 0 && i != 1) {
                    //                    result.append(",");
                }
                result.append(intString.substring(intString.length - i, intString.length - i + 1))
            }
            result.reverse().append(".").append(amString.substring(amString.length - 2))
        }
        if (flag == 1) {
            return "-" + result.toString()
        } else {
            return result.toString()
        }
    }

    /*
        分 转换为 元
     */
    fun changeF2Y(amount: String, defaultString: String): String {
        var result = defaultString
        try {
            result = changeF2Y(amount)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return result
    }

    /*
        分 转换为 元
     */
    @Throws(Exception::class)
    fun changeF2Y(amount: String): String {
        if (!amount.matches(CURRENCY_FEN_REGEX.toRegex())) {
            throw Exception("Invalid format")
        }
        return BigDecimal.valueOf(java.lang.Long.valueOf(amount)!!).divide(BigDecimal(100)).toString()
    }

    /*
        元 转换为 分
     */
    fun changeY2F(amount: Long?): String {
        return BigDecimal.valueOf(amount!!).multiply(BigDecimal(100)).toString()
    }

    /*
        元 转换为 分
     */
    fun changeY2F(amount: String): String {
        val currency = amount.replace("\\$|\\¥|\\,".toRegex(), "")
        val index = currency.indexOf(".")
        val length = currency.length
        var amLong: Long? = 0L
        if (index == -1) {
            amLong = java.lang.Long.valueOf(currency + "00")
        } else if (length - index >= 3) {
            amLong = java.lang.Long.valueOf(currency.substring(0, index + 3).replace(".", ""))
        } else if (length - index == 2) {
            amLong = java.lang.Long.valueOf(currency.substring(0, index + 2).replace(".", "") + 0)
        } else {
            amLong = java.lang.Long.valueOf(currency.substring(0, index + 1).replace(".", "") + "00")
        }
        return amLong!!.toString()
    }

    /*
        分 转换为 元，带单位
     */
    fun changeF2YWithUnit(amount:Long):String{
        return "¥${YuanFenConverter.changeF2Y(amount)}"
    }
}
