package com.hraps.ianserver.mapper

import com.hraps.ianserver.entity.App
import org.apache.ibatis.annotations.Mapper
import org.springframework.web.bind.annotation.GetMapping

@Mapper
interface AppMapper : BaseMapper<App> {
    @GetMapping
    fun selectByName(name: String): App?

}