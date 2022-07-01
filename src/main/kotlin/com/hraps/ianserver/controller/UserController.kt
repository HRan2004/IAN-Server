package com.hraps.ianserver.controller

import com.hraps.ianserver.utils.Secret
import com.hraps.ianserver.service.CardService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserController: BaseController() {

    @Autowired
    lateinit var cardService: CardService

    @GetMapping("/check")
    fun check(
        @RequestParam("app") app: String,
        @RequestParam("card") card: String,
        @RequestParam("device") device: String,
        @RequestParam("nonce") nonce: String,
        @RequestParam("sign") sign: String
    ): String {
        val result = HashMap<String, Any>()

        val checkStr = "$app$card$device"
        if(!Secret.check(checkStr, nonce, sign)) {
            result["code"] = -1
            result["msg"] = "签名校验错误"
            return json(result)
        }
        val data = cardService.checkCard(app, card, device)
        if (data["success"]==false) {
            result["msg"] = data["msg"]?:"未知错误"
            result["code"] = -1
            return json(result)
        }
        data.remove("success")
        result["msg"] = "验证成功"
        result["code"] = 0
        result["data"] = data
        return json(data)
    }

}