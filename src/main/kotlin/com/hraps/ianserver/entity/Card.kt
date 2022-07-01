package com.hraps.ianserver.entity

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import java.util.Date

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("card")
class Card(
) {
    @TableId(type = IdType.AUTO)
    val id: Int? = null
    var value: String = ""
    var status: Int = STATUS_UNUSED
    var aid: Int? = null
    var device: String = ""
    var duration: Int = -1
    var validity: Date? = null

    companion object {
        const val STATUS_LOCKED = -1
        const val STATUS_UNUSED = 0
        const val STATUS_USED = 1
        const val STATUS_EXPIRED = 2
    }
}

