package com.example.selfupdate.net.exception

import java.lang.Exception

/**
 * Created by zb on 2021/3/31 16:55
 */
data class CommonException(val code:Int, val msg:Any) :Exception()