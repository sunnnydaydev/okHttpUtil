package com.example.selfupdate.net.ssl

import java.lang.Exception
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509KeyManager
import javax.net.ssl.X509TrustManager

/**
 * Created by zb on 2021/3/31 16:06
 */
object HttpUtils {

    fun getSslSocketFactory(): SSLSocketFactory {
        val manager: X509TrustManager = object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {

            }

            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {

            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        }
        var sslContext: SSLContext? = null
        try {
            sslContext = SSLContext.getInstance("SSL")
            val xTrustArray: Array<X509TrustManager> = arrayOf(manager)
            sslContext.init(null, xTrustArray, SecureRandom())
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return sslContext!!.socketFactory
    }
}