package com.example.selfupdate.net.Request

import java.util.concurrent.ConcurrentHashMap

/**
 * Created by zb on 2021/3/31 14:33
 *
 * 请求参数封装成 map，便于统一处理。
 */
class RequestParams {

     val urlParams: ConcurrentHashMap<String, String> = ConcurrentHashMap()


    constructor(source: Map<String, String>) {
        for (entry: Map.Entry<String, String> in source.entries) {
            put(entry.key, entry.value)
        }
    }

    constructor(key: String, value: String) : this(mapOf<String, String>().apply { key to value })

    constructor() : this(mapOf<String, String>().apply {})


    /**
     * put url params into map
     * */
    private fun put(key: String, value: String) {
        urlParams[key] = value
    }

    fun hasParams(): Boolean {
        if (urlParams.size > 0) return true
        return false
    }
}