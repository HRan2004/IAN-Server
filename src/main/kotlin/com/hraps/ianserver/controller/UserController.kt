package com.hraps.ianserver.controller

import com.hraps.ianserver.utils.Secret
import com.hraps.ianserver.service.CardService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.Date

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
        @RequestParam("time") time: Long,
        @RequestParam("nonce") nonce: String,
        @RequestParam("sign") sign: String
    ): String {
        val result = HashMap<String, Any>()

        val checkStr = "$app$card$device$time"
        if(!Secret.checkSign(checkStr, nonce, sign)) {
            result["code"] = -1
            result["msg"] = "签名校验错误"
            return signJson(result)
        }
        if(time+5000<Date().time) {
            result["code"] = -1
            result["msg"] = "请求超时"
            return signJson(result)
        }
        val data = cardService.checkCard(card, app, device)
        if (data["success"]==false) {
            result["msg"] = data["msg"]?:"未知错误"
            result["code"] = -1
            return signJson(result)
        }
        if(data["bind"] as Boolean){
            result["code"] = 1
            result["msg"] = "新卡绑定成功"
        }else{
            result["code"] = 0
            result["msg"] = "验证成功"
        }
        data.remove("success")
        data.remove("msg")
        result["data"] = data
        return signJson(result)
    }
    fun signJson(map: HashMap<String, Any>): String {
        return json(Secret.signMapWithCode(map))
    }

}