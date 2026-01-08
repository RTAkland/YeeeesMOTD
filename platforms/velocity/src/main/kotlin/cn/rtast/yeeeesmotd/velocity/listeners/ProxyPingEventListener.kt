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


package cn.rtast.yeeeesmotd.velocity.listeners

import cn.rtast.yeeeesmotd.IYeeeesMOTD.Companion.configManager
import cn.rtast.yeeeesmotd.IYeeeesMOTD.Companion.defaultIcon
import cn.rtast.yeeeesmotd.IYeeeesMOTD.Companion.fakeSamplePlayerGenerator
import cn.rtast.yeeeesmotd.IYeeeesMOTD.Companion.faviconManager
import cn.rtast.yeeeesmotd.IYeeeesMOTD.Companion.hitokotoManager
import cn.rtast.yeeeesmotd.IYeeeesMOTD.Companion.pingRecordManager
import cn.rtast.yeeeesmotd.IYeeeesMOTD.Companion.skinHeadManager
import cn.rtast.yeeeesmotd.utils.nextBoolean
import cn.rtast.yeeeesmotd.utils.str.Color
import cn.rtast.yeeeesmotd.utils.str.toUUID
import cn.rtast.yeeeesmotd.velocity.YeeeesMOTDPlugin.Companion.miniMessage
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyPingEvent
import com.velocitypowered.api.proxy.server.ServerPing
import com.velocitypowered.api.util.Favicon
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.Style
import net.kyori.adventure.text.format.TextColor
import java.io.ByteArrayInputStream
import java.util.*
import javax.imageio.ImageIO
import kotlin.random.Random

class ProxyPingEventListener {

    @Subscribe
    fun onProxyPingEvent(event: ProxyPingEvent) {
        val ip = event.connection.remoteAddress.hostName

        var favicon = Favicon.create(defaultIcon)
        val showHead = nextBoolean(configManager.ipFingerprint().probability)

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

        val p = configManager.hitokoto().probability
        val showHitokoto = nextBoolean(p)
        if (configManager.hitokoto().enabled && showHitokoto) {
            val color = configManager.hitokoto().color
            val hitokoto = hitokotoManager.getSentence()
            finalDescription = Component.text()
                .append(miniMessage.deserialize("<$color>${hitokoto.line1}"))
                .append(Component.text("\n"))
                .append(miniMessage.deserialize("<$color>${hitokoto.line2}"))
        }


        if (showHead && skinHeadManager.exists(ip) && configManager.ipFingerprint().enabled) {
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

        val randomProtocolVersion = configManager.getRandomProtocolNumber() ?: event.ping.version.protocol
        val randomProtocolName = configManager.getRandomProtocolName() ?: event.ping.version.name

        val pong = event.ping.asBuilder()
        pong.description(finalDescription.build())
            .favicon(favicon)

        if (configManager.pingList().maximumPlayerEnabled) {
            pong.maximumPlayers(configManager.pingList().maximumPlayer)
        }
        if (configManager.pingList().onlinePlayerEnabled) {
            pong.onlinePlayers(configManager.pingList().onlinePlayer)
        }

        if (configManager.fakeProtocol().enabled) {
            pong.version(
                ServerPing.Version(
                    randomProtocolVersion,
                    Color.convertMiniMessage(randomProtocolName)
                        .replace("<onlinePlayer>", pong.onlinePlayers.toString())
                        .replace("<maxPlayer>", pong.maximumPlayers.toString())
                )
            )
        }

        if (configManager.fakeSamplePlayer().enabled) {
            var fakeSamplePlayerCount = configManager.fakeSamplePlayer().fakePlayersCount
            if (fakeSamplePlayerCount > 400) {
                println("DO NOT SET THE FAKE PLAYER COUNT MORE THAN 400! ALREADY SET THE COUNT TO 10")
                fakeSamplePlayerCount = 10
            }
            val fakePlayers = fakeSamplePlayerGenerator.generate(fakeSamplePlayerCount)
            val samplePlayers = mutableListOf<ServerPing.SamplePlayer>()
            fakePlayers.forEach { player ->
                samplePlayers.add(ServerPing.SamplePlayer(player.name, player.uuid.toUUID()))
            }
            pong.samplePlayers(*samplePlayers.toTypedArray())
        }

        if (configManager.pingList().clearSamplePlayer) {
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
