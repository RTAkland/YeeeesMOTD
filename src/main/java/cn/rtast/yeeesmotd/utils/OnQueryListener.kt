/*
 * Copyright 2024 RTAkland
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */


package cn.rtast.yeeesmotd.utils

import cn.rtast.yeeesmotd.DEFAULT_ICON
import cn.rtast.yeeesmotd.YeeeesMOTDPlugin
import com.velocitypowered.api.proxy.server.ServerPing
import com.velocitypowered.api.util.Favicon
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import java.util.*
import javax.imageio.ImageIO
import kotlin.random.Random


fun onQuery(ping: ServerPing, ip: String): ServerPing {
    val pong = ping.asBuilder()

    var favicon = Favicon(DEFAULT_ICON)
    val showHead = Random.nextBoolean()
    val randomDescription = YeeeesMOTDPlugin.descriptionManager.randomDescription()
    var finalDescription = Component.text()

    if (randomDescription == null) {
        finalDescription.append(ping.descriptionComponent)
    } else {
        val miniLine1 = YeeeesMOTDPlugin.miniMessage.deserialize(randomDescription.line1)
        val miniLine2 = YeeeesMOTDPlugin.miniMessage.deserialize(randomDescription.line2)
        finalDescription.append(miniLine1).append(Component.text("\n")).append(miniLine2)
    }

    if (showHead && YeeeesMOTDPlugin.skinHeadManager.exists(ip)) {
        val userData = YeeeesMOTDPlugin.skinHeadManager.getHead(ip)
        val decodedHead = Base64.getDecoder().decode(userData.head)
        val bufferedHead = ImageIO.read(decodedHead.inputStream())
        val randomBuildInDesc = YeeeesMOTDPlugin.descriptionManager.randomBuildInDesc().replace("\$player", userData.name)
        favicon = Favicon.create(bufferedHead)
        finalDescription = Component.text(randomBuildInDesc).toBuilder()
            .style { it.color(TextColor.color(0x00FF33)) }
    } else {
        val randomIcon = YeeeesMOTDPlugin.faviconManager.getRandomIcon()
        if (randomIcon != null) {
            favicon = Favicon.create(randomIcon)
        }
    }

    pong.description(finalDescription.build())
        .favicon(favicon)
        .onlinePlayers(-1)
        .maximumPlayers(-1)
    return pong.build()

}
