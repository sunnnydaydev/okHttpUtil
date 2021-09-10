package com.example.selfupdate.net.response

import android.os.Handler
import android.os.Looper
import com.example.selfupdate.net.exception.CommonException
import com.example.selfupdate.net.listener.DisposeDataHandler
import com.example.selfupdate.net.listener.DisposeDataListener
import com.example.selfupdate.beans.AppUpdateInfo
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.lang.Exception

/**
 * Created by zb on 2021/3/31 16:00
 *
 * CommonJson callBack, Convert stream data to json String !
 */

class CommonJsonCallback(
    disposeDataHandler: DisposeDataHandler
) : Callback {

    companion object{
        private const val NETWORK_ERROR: Int = -1
        private const val JSON_ERROR: Int = -2
        private const val OTHER_ERROR: Int = -3
    }

    private var listener: DisposeDataListener = disposeDataHandler.listener
    private var mHandler: Handler = Handler(Looper.getMainLooper())
    private val mClass: Class<*>? = disposeDataHandler.clazz


    override fun onFailure(call: Call, e: IOException?) {
        mHandler.post {
            listener.onFailure(CommonException(NETWORK_ERROR, "net work error!"))
        }
    }

    override fun onResponse(call: Call, response: Response?) {
        val json = response!!.body().string()
        mHandler.post {
            handleStringJson(json)
        }
    }


    private fun handleStringJson(jsonStr: String) {

        if (mClass == null) {
            listener.onSuccess(jsonStr)
        } else {

            try {
                val gson = Gson()
                val obj = gson.fromJson(jsonStr, mClass)
                if (obj != null) {
                    listener.onSuccess(obj)
                } else {
                    listener.onFailure(CommonException(JSON_ERROR, "parse json error!"))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                listener.onFailure(CommonException(JSON_ERROR, "parse json error!"))
            }

        }
    }
}