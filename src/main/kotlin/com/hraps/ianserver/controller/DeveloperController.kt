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
        @RequestParam("ak") akStr: String,
        @RequestParam("duration") duration: Int,
    ): String {
        val ak = akStr.split(":")
        if(ak.size != 2) return "管理密钥无效"
        if (!developerService.checkDeveloperPermission(ak[0], ak[1])) return "权限校验失败"
        val card = cardService.newCard(ak[0], duration)
        return Gson().toJson(card)
    }

    @GetMapping("/list")
    fun listCard(
        @RequestParam("ak") akStr: String,
        @RequestParam(value = "page", defaultValue = "1") page: Int,
    ): String {
        val ak = akStr.split(":")
        if(ak.size != 2) return "管理密钥无效"
        if (!developerService.checkDeveloperPermission(ak[0], ak[1])) return "权限校验失败"
        val cards = cardService.listCard(ak[0], page) ?: return "APP不存在"
        // return Gson().toJson(cards)
        return "<p>"+cards.joinToString("</p><p>") { Gson().toJson(it) }+"</p>"
    }

    @GetMapping("/lock")
    fun deleteCard(
        @RequestParam("ak") akStr: String,
        @RequestParam("card") card: String,
    ): String {
        val ak = akStr.split(":")
        if(ak.size != 2) return "管理密钥无效"
        if (!developerService.checkDeveloperPermission(ak[0], ak[1])) return "权限校验失败"
        val result = cardService.lockCard(card)
        return if (result) "禁用成功" else "禁用失败"
    }

    @GetMapping("/unbind")
    fun unbindCard(
        @RequestParam("ak") akStr: String,
        @RequestParam("card") card: String,
    ): String {
        val ak = akStr.split(":")
        if(ak.size != 2) return "管理密钥无效"
        if (!developerService.checkDeveloperPermission(ak[0], ak[1])) return "权限校验失败"
        val result = cardService.unbindCard(card)
        return if (result) "解绑成功" else "解绑失败"
    }
}