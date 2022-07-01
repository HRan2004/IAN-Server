package com.hraps.ianserver.entity

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("app")
class App(
) {
    @TableId(type = IdType.AUTO)
    val aid: Int? = null
    val name: String = ""
    val limit: Int = 100
    val key: String = ""
    val tips: String = ""
}
