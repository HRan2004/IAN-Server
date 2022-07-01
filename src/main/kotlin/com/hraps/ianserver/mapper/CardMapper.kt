package com.hraps.ianserver.mapper

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.hraps.ianserver.entity.App
import com.hraps.ianserver.entity.Card
import org.apache.ibatis.annotations.Mapper

@Mapper
interface CardMapper: BaseMapper<Card> {

    fun selectByValueAndAid (value: String, aid: Int): Card?

    fun selectAllByAid (aid: Int): List<Card>

    fun updateStatusById (id: Int, status: Int): Int

}