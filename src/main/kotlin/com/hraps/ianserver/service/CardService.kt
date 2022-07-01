package com.hraps.ianserver.service

import com.hraps.ianserver.utils.Secret
import com.hraps.ianserver.entity.Card
import com.hraps.ianserver.mapper.AppMapper
import com.hraps.ianserver.mapper.CardMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.Date

@Service
class CardService {

    @Autowired
    lateinit var cardMapper: CardMapper
    @Autowired
    lateinit var appMapper: AppMapper

    fun newCard(appName: String, duration: Int): Card? {
        val card = Card()
        val apps = appMapper.selectByName(appName)
        if (apps.isEmpty()) return null
        val app = apps[0]
        card.duration = duration
        card.aid = app.aid
        card.value = Secret.newCardNo()
        if(cardMapper.insert(card)<1) return null
        return card
    }

    fun listCard(appName: String, page: Int): List<Card>? {
        val apps = appMapper.selectByName(appName)
        if (apps.isEmpty()) return null
        val app = apps[0]
        return cardMapper.selectByAid(app.aid!!)
    }

    fun lockCard(card: String): Boolean {
        val cards = cardMapper.selectByValue(card)
        if (cards.isEmpty()) return false
        val card = cards[0]
        return cardMapper.updateStatusById(card.id!!, Card.STATUS_LOCKED)>0
    }

    fun checkCard(card: String, app: String, device: String): HashMap<String, Any> {
        val map = HashMap<String, Any>()
        map["success"] = false
        val apps = appMapper.selectByName(app)
        if (apps.isEmpty()) {
            map["msg"] = "App不存在"
            return map
        }
        val app = apps[0]
        val cards = cardMapper.selectByValueAndAid(card, app.aid!!)
        if (cards.isEmpty()) {
            map["msg"] = "卡密不存在"
            return map
        }
        val card = cards[0]
        if (card.status == Card.STATUS_EXPIRED) {
            map["msg"] = "卡密过期"
            return map
        }
        if (card.status == Card.STATUS_LOCKED) {
            map["msg"] = "卡密已停用"
            return map
        }
        if(card.status == Card.STATUS_USED){
            if(card.validity!!.time < Date().time){
                cardMapper.updateStatusById(card.id!!, Card.STATUS_EXPIRED)
                map["msg"] = "卡密已过期"
                return map
            }
            if(card.device != device) {
                map["msg"] = "卡密不属于本设备"
                return map
            }
        }
        map["success"] = true
        map["card"] = card.value
        if(card.status == Card.STATUS_UNUSED){
            map["bind"] = true
            map["msg"] = "新设备已绑定"
            card.status = Card.STATUS_USED
            card.device = device
            card.validity = Date(Date().time + card.duration * 24 * 60 * 60 * 1000)
            cardMapper.updateById(card)
        }
        map["validity"] = card.validity?.time ?: 0
        if(card.status == Card.STATUS_USED){
            map["bind"] = false
            map["msg"] = "验证成功"
        }
        map["tips"] = app.tips

        return map
    }
}