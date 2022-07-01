package com.hraps.ianserver.controller

import com.google.gson.Gson

open class BaseController {
    fun json(map: Map<String, Any>): String {
        return Gson().toJson(map)
    }
}