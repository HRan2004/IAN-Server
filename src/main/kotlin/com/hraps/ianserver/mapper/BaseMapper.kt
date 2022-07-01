package com.hraps.ianserver.mapper

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import org.apache.ibatis.annotations.Mapper

interface BaseMapper<T>: com.baomidou.mybatisplus.core.mapper.BaseMapper<T> {
    fun selectOne(queryWrapper: QueryWrapper<T>): T? {
        val list = selectList(queryWrapper)
        return if (list.isEmpty()) null else list[0]
    }
}