package com.hraps.ianserver

import com.google.gson.Gson
import com.hraps.ianserver.mapper.AppMapper
import com.hraps.ianserver.mapper.CardMapper
import com.hraps.ianserver.service.CardService
import com.hraps.ianserver.service.DeveloperService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class IanServerApplicationTests {

    @Autowired
    lateinit var cardMapper: CardMapper
    @Autowired
    lateinit var appMapper: AppMapper
    @Autowired
    lateinit var cardService: CardService
    @Autowired
    lateinit var developerService: DeveloperService

    @Test
    fun test() {
        // val r = appMapper.selectByName("IAN")
        val r = cardService.newCard("IAN", 3)
        println(Gson().toJson(r))
    }

    @Test
    fun checkCard() {
        val r = cardService.checkCard(
            "C47BD60849D5A7492F44",
            "IAN",
            "AAAABBBB"
        )
        println(r)
    }

}
