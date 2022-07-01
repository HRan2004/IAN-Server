package com.hraps.ianserver.service

import com.hraps.ianserver.mapper.AppMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DeveloperService {
    @Autowired
    lateinit var appMapper: AppMapper

    fun checkDeveloperPermission(appName: String, appKey: String): Boolean {
        val app = appMapper.selectByName(appName)
        app?: return false
        return app.key == appKey
    }
}