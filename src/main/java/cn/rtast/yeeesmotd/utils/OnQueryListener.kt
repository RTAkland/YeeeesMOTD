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
    val randomDescription = YeeeesMOTDPlugin.configManager.getRandomDescription();

    var finalDescription = Component.text()
    if (randomDescription == null) {
        finalDescription.append(ping.descriptionComponent)
    } else {
        finalDescription
            .append(YeeeesMOTDPlugin.miniMessage.deserialize(randomDescription.line1))
            .append(Component.text("\n"))
            .append(YeeeesMOTDPlugin.miniMessage.deserialize(randomDescription.line2))
    }

    if (showHead && YeeeesMOTDPlugin.skinHeadManager.exists(ip)) {
        val userData = YeeeesMOTDPlugin.skinHeadManager.getHead(ip)
        val decodedHead = Base64.getDecoder().decode(userData.head)
        val bufferedHead = ImageIO.read(decodedHead.inputStream())
        favicon = Favicon.create(bufferedHead)

        val randomBuildInDesc =
            YeeeesMOTDPlugin.configManager.getRandomBuildInDescription().split("\$player", userData.name)
        finalDescription = Component.text()
            .append(YeeeesMOTDPlugin.miniMessage.deserialize(randomBuildInDesc.first()))
            .append(Component.text(userData.name).style { it.color(TextColor.color(0xEE82EE)) })
            .append(YeeeesMOTDPlugin.miniMessage.deserialize(randomBuildInDesc.last()))
    } else {
        val randomIcon = YeeeesMOTDPlugin.faviconManager.getRandomIcon()
        if (randomIcon != null) {
            favicon = Favicon.create(randomIcon)
        }
    }

    val playerConfig = YeeeesMOTDPlugin.configManager.getConfig()

    pong.description(finalDescription.build())
        .favicon(favicon)
        .onlinePlayers(playerConfig.onlinePlayer)
        .maximumPlayers(playerConfig.maximumPlayer)

    if (playerConfig.clearSamplePlayer) {
        pong.clearSamplePlayers()
    }
    return pong.build()

}
