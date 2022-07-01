package com.hraps.ianserver.mapper

import com.hraps.ianserver.entity.Card
import org.apache.ibatis.annotations.Mapper

@Mapper
interface CardMapper: BaseMapper<Card> {

    fun selectOneByValueAndAid (value: String, aid: Int): Card?

    fun selectByAid (aid: Int): List<Card>

    fun updateStatusById (id: Int, status: Int): Int

}