package com.hraps.ianserver.mapper

import com.hraps.ianserver.entity.Card
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update

@Mapper
interface CardMapper: BaseMapper<Card> {

    @Select("SELECT * FROM card WHERE value = #{value} AND aid = #{aid}")
    fun selectOneByValueAndAid (value: String, aid: Int): Card?

    @Select("SELECT * FROM card WHERE aid = #{aid} ORDER BY id DESC")
    fun selectByAid (aid: Int): List<Card>

    @Update("UPDATE card SET status = #{status} WHERE id = #{id}")
    fun updateStatusById (id: Int, status: Int): Integer

    @Select("SELECT * FROM card WHERE value = #{value}")
    fun selectOneByValue(card: String): Card?

}