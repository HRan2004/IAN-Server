package com.hraps.ianserver.controller

import com.google.gson.Gson
import com.hraps.ianserver.service.CardService
import com.hraps.ianserver.service.DeveloperService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/developer")
class DeveloperController : BaseController() {

    @Autowired
    lateinit var developerService: DeveloperService

    @Autowired
    lateinit var cardService: CardService

    @GetMapping("/new")
    fun newCard(
        @RequestParam("app") app: String,
        @RequestParam("key") key: String,
        @RequestParam("duration") duration: Int,
    ): String {
        if (!developerService.checkDeveloperPermission(app, key)) return "权限校验失败"
        val card = cardService.newCard(app, duration)
        return Gson().toJson(card)
    }

    @GetMapping("/list")
    fun listCard(
        @RequestParam("app") app: String,
        @RequestParam("key") key: String,
        @RequestParam(value = "page", defaultValue = "1") page: Int,
    ): String {
        if (!developerService.checkDeveloperPermission(app, key)) return "权限校验失败"
        val cards = cardService.listCard(app, page) ?: return "APP不存在"
        return Gson().toJson(cards)
    }

    @GetMapping("/delete")
    fun deleteCard(
        @RequestParam("app") app: String,
        @RequestParam("key") key: String,
        @RequestParam("card") card: String,
    ): String {
        return ""
    }
}