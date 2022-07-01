package com.hraps.ianserver.mapper

import com.hraps.ianserver.entity.Card
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update

@Mapper
interface CardMapper: BaseMapper<Card> {

    fun selectOneByValueAndAid (value: String, aid: Int): Card?

    fun selectByAid (aid: Int): List<Card>

    @Update("UPDATE card SET status = #{status} WHERE id = #{id}")
    fun updateStatusById (id: Int, status: Int): Integer

    fun selectOneByValue(card: String): Card?

}