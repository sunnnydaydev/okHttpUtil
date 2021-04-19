package com.example.selfupdate.net.listener


/**
 * Created by zb on 2021/3/31 16:34
 */
interface DisposeDataListener {

    fun onSuccess(responseJson: Any)
    fun onFailure(responseJson: Any)
    fun onBefore(responseJson: Any)

}