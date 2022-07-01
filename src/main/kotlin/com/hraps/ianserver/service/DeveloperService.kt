package com.hraps.ianserver.service

import com.hraps.ianserver.mapper.AppMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DeveloperService {
    @Autowired
    lateinit var appMapper: AppMapper

    fun checkDeveloperPermission(appName: String, appKey: String): Boolean {
        val apps = appMapper.selectByName(appName)
        if (apps.isEmpty()) return false
        val app = apps[0]
        return app.key == appKey
    }
}