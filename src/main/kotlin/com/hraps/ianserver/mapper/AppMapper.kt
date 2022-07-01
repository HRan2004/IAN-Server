package com.hraps.ianserver.mapper

import com.hraps.ianserver.entity.App
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select
import org.springframework.web.bind.annotation.GetMapping

@Mapper
interface AppMapper : BaseMapper<App> {

    @Select("SELECT * FROM app WHERE name = #{name}")
    @GetMapping
    fun selectByName(name: String): App?

}