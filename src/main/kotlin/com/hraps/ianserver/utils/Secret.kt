package com.hraps.ianserver.utils

import java.security.MessageDigest
import java.util.*
import kotlin.collections.HashMap
import kotlin.random.Random

class Secret {
    companion object {
        val random = Random(System.currentTimeMillis())

        fun signMapWithCode(map: HashMap<String, Any>): HashMap<String, Any> {
            map["time"] = Date().time
            map["nonce"] = md5(""+ random.nextInt(100000,999999))
            val str = "" + map["code"] + map["time"] + map["nonce"]
            // println(str)
            map["sign"] = md5(str, Key.SALT)
            return map
        }
        fun checkSign(str: String, nonce: String, sign: String): Boolean {
            return md5(str + nonce, Key.SALT) == sign
        }

        fun newCardNo(): String {
            val millis = System.currentTimeMillis()
            val sourceStr = "" + millis + random.nextInt(1000,9999) + "CARD-SALT"
            return md5(sourceStr).substring(0,20).uppercase()
        }


        private fun md5(str: String, salt: String = ""): String {
            val digest = MessageDigest.getInstance("MD5")
            val result = digest.digest((str+salt).toByteArray())
            return toHex(result)
        }
        private fun toHex(byteArray: ByteArray): String {
            val result = with(StringBuilder()) {
                byteArray.forEach {
                    val hex = it.toInt() and (0xFF)
                    val hexStr = Integer.toHexString(hex)
                    if (hexStr.length == 1) {
                        this.append("0").append(hexStr)
                    } else {
                        this.append(hexStr)
                    }
                }
                this.toString()
            }
            return result
        }



    }
}