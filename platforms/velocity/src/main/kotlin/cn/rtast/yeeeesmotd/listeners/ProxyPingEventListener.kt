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


package cn.rtast.yeeeesmotd.listeners

import cn.rtast.yeeeesmotd.DEFAULT_ICON
import cn.rtast.yeeeesmotd.YeeeesMOTDPlugin.Companion.configManager
import cn.rtast.yeeeesmotd.YeeeesMOTDPlugin.Companion.faviconManager
import cn.rtast.yeeeesmotd.YeeeesMOTDPlugin.Companion.miniMessage
import cn.rtast.yeeeesmotd.YeeeesMOTDPlugin.Companion.pingRecordManager
import cn.rtast.yeeeesmotd.YeeeesMOTDPlugin.Companion.skinHeadManager
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyPingEvent
import com.velocitypowered.api.util.Favicon
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.Style
import net.kyori.adventure.text.format.TextColor
import java.io.ByteArrayInputStream
import java.util.*
import javax.imageio.ImageIO

class ProxyPingEventListener {

    @Subscribe
    fun onProxyPingEvent(event: ProxyPingEvent) {
        val ip = event.connection.remoteAddress.hostName

        var favicon = Favicon(DEFAULT_ICON)
        val showHead = Random().nextBoolean()

        val randomDescription = configManager.getRandomDescription()
        var finalDescription = Component.text()
        if (randomDescription == null) {
            finalDescription.append(event.ping.descriptionComponent)
        } else {
            finalDescription
                .append(miniMessage.deserialize(randomDescription.line1))
                .append(Component.text("\n"))
                .append(miniMessage.deserialize(randomDescription.line2))
        }

        if (showHead && skinHeadManager.exists(ip)) {
            val userData = skinHeadManager.getHead(ip)
            val decodedHead = Base64.getDecoder().decode(userData.head)
            val bufferedHead = ImageIO.read(ByteArrayInputStream(decodedHead))
            favicon = Favicon.create(bufferedHead)

            val randomBuildInDesc =
                configManager.getRandomBuildInDescription().split("\$player")
            finalDescription = Component.text()
                .append(miniMessage.deserialize(randomBuildInDesc[0]))
                .append(
                    Component.text(userData.name)
                        .style { style: Style.Builder -> style.color(TextColor.color(0xEE82EE)) })
                .append(miniMessage.deserialize(randomBuildInDesc[randomBuildInDesc.size - 1]))
        } else {
            val randomIcon = faviconManager.getRandomIcon()
            if (randomIcon != null) {
                favicon = Favicon.create(randomIcon)
            }
        }

        val pong = event.ping.asBuilder()
        pong.description(finalDescription.build())
            .favicon(favicon)
            .onlinePlayers(configManager.getConfig().onlinePlayer)
            .maximumPlayers(configManager.getConfig().maximumPlayer)

        if (configManager.getConfig().clearSamplePlayer) {
            pong.clearSamplePlayers()
        }

        event.ping = pong.build()

        if (configManager.pingPass().enabled) {
            if (pingRecordManager.exists(ip)) {
                pingRecordManager.removeRecord(ip)
            }
            pingRecordManager.addRecord(ip)
        }
    }
}
