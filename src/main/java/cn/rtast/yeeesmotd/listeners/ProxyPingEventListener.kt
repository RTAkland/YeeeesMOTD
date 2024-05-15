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
package cn.rtast.yeeesmotd.listeners

import cn.rtast.yeeesmotd.YeeeesMOTDPlugin
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyPingEvent
import com.velocitypowered.api.util.Favicon
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.Style
import net.kyori.adventure.text.format.TextColor
import java.io.ByteArrayInputStream
import java.io.IOException
import java.util.*
import javax.imageio.ImageIO

class ProxyPingEventListener {
    @Subscribe
    @Throws(IOException::class)
    fun onProxyPingEvent(event: ProxyPingEvent) {
        val ip = event.connection.remoteAddress.hostName

        var favicon: Favicon? = Favicon(DEFAULT_ICON)
        val showHead = Random().nextBoolean()

        val randomDescription = YeeeesMOTDPlugin.configManager.getRandomDescription()
        var finalDescription = Component.text()
        if (randomDescription == null) {
            finalDescription.append(event.ping.descriptionComponent)
        } else {
            finalDescription
                .append(YeeeesMOTDPlugin.miniMessage.deserialize(randomDescription.line1))
                .append(Component.text("\n"))
                .append(YeeeesMOTDPlugin.miniMessage.deserialize(randomDescription.line2))
        }

        if (showHead && YeeeesMOTDPlugin.skinHeadManager.exists(ip)) {
            val userData = YeeeesMOTDPlugin.skinHeadManager.getHead(ip)
            val decodedHead = Base64.getDecoder().decode(userData.head)
            val bufferedHead = ImageIO.read(ByteArrayInputStream(decodedHead))
            favicon = Favicon.create(bufferedHead)

            val randomBuildInDesc =
                YeeeesMOTDPlugin.configManager.getRandomBuildInDescription().split("\\\$player".toRegex())
                    .dropLastWhile { it.isEmpty() }
                    .toTypedArray()
            finalDescription = Component.text()
                .append(YeeeesMOTDPlugin.miniMessage.deserialize(randomBuildInDesc[0]))
                .append(
                    Component.text(userData.name)
                        .style { style: Style.Builder -> style.color(TextColor.color(0xEE82EE)) })
                .append(YeeeesMOTDPlugin.miniMessage.deserialize(randomBuildInDesc[randomBuildInDesc.size - 1]))
        } else {
            val randomIcon = YeeeesMOTDPlugin.faviconManager.getRandomIcon()
            if (randomIcon != null) {
                favicon = Favicon.create(randomIcon)
            }
        }

        val pong = event.ping.asBuilder()
        pong.description(finalDescription.build())
            .favicon(favicon)
            .onlinePlayers(YeeeesMOTDPlugin.configManager.getConfig().onlinePlayer)
            .maximumPlayers(YeeeesMOTDPlugin.configManager.getConfig().maximumPlayer)

        if (YeeeesMOTDPlugin.configManager.getConfig().clearSamplePlayer) {
            pong.clearSamplePlayers()
        }

        event.ping = pong.build()

        if (YeeeesMOTDPlugin.configManager.pingPass().enabled) {
            if (YeeeesMOTDPlugin.pingRecordManager.exists(ip)) {
                YeeeesMOTDPlugin.pingRecordManager.removeRecord(ip)
            }
            YeeeesMOTDPlugin.pingRecordManager.addRecord(ip)
        }
    }
}
