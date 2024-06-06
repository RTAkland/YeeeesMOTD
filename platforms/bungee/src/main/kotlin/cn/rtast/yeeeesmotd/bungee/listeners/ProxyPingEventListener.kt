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


package cn.rtast.yeeeesmotd.bungee.listeners

import cn.rtast.yeeeesmotd.IYeeeesMOTD.Companion.configManager
import cn.rtast.yeeeesmotd.IYeeeesMOTD.Companion.defaultIcon
import cn.rtast.yeeeesmotd.IYeeeesMOTD.Companion.fakeSamplePlayerGenerator
import cn.rtast.yeeeesmotd.IYeeeesMOTD.Companion.faviconManager
import cn.rtast.yeeeesmotd.IYeeeesMOTD.Companion.hitokotoManager
import cn.rtast.yeeeesmotd.IYeeeesMOTD.Companion.pingRecordManager
import cn.rtast.yeeeesmotd.IYeeeesMOTD.Companion.skinHeadManager
import cn.rtast.yeeeesmotd.bungee.YeeeesMOTDPlugin.Companion.miniMessage
import cn.rtast.yeeeesmotd.utils.nextBoolean
import cn.rtast.yeeeesmotd.utils.str.toUUID
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer
import net.md_5.bungee.api.Favicon
import net.md_5.bungee.api.ServerPing
import net.md_5.bungee.api.ServerPing.PlayerInfo
import net.md_5.bungee.api.event.ProxyPingEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler
import java.io.ByteArrayInputStream
import java.util.*
import javax.imageio.ImageIO
import kotlin.random.Random

class ProxyPingEventListener : Listener {

    @EventHandler
    fun onProxyPingEvent(event: ProxyPingEvent) {
        val ip = event.connection.socketAddress.toString().split(":").first().replace("/", "")

        var favicon = Favicon.create(defaultIcon)
        val showHead = Random.nextBoolean()

        val randomDescription = configManager.getRandomDescription()
        val finalDescription = StringBuilder()

        if (randomDescription == null) {
            finalDescription.append(event.response.descriptionComponent)
        } else {
            finalDescription.append("${randomDescription.line1}\n${randomDescription.line2}")
        }

        val p = configManager.hitokoto().probability
        val showHitokoto = nextBoolean(p)
        if (configManager.hitokoto().enabled && showHitokoto) {
            val hitokoto = hitokotoManager.getSentence()
            val color = configManager.hitokoto().color
            finalDescription.clear()
                .append("<$color>${hitokoto.line1}\n${hitokoto.line2}")
        }


        if (showHead && skinHeadManager.exists(ip) && configManager.ipFingerprint().enabled) {
            val userData = skinHeadManager.getHead(ip)
            val decodedHead = Base64.getDecoder().decode(userData.head)
            val bufferedHead = ImageIO.read(ByteArrayInputStream(decodedHead))
            favicon = Favicon.create(bufferedHead)

            val randomBuildInDesc =
                configManager.getRandomBuildInDescription().split("\$player")
            finalDescription.clear()
                .append(randomBuildInDesc.first())
                .append("<#EE82EE>${userData.name}")
                .append(randomBuildInDesc.last()).last()
        } else {
            val randomIcon = faviconManager.getRandomIcon()
            if (randomIcon != null) {
                favicon = Favicon.create(randomIcon)
            }
        }

        val deserializedDescription =
            BungeeComponentSerializer.get().serialize(miniMessage.deserialize(finalDescription.toString()))

        val randomProtocolVersion = configManager.getRandomProtocolNumber() ?: event.response.version.protocol
        val randomProtocolName = configManager.getRandomProtocolName() ?: event.response.version.name

        event.response.descriptionComponent = deserializedDescription.first()
        event.response.setFavicon(favicon)
        event.response.players.max = configManager.getConfig().maximumPlayer
        event.response.players.online = configManager.getConfig().onlinePlayer
        if (configManager.fakeProtocol().enabled) {
            event.response.version = ServerPing.Protocol(randomProtocolName, randomProtocolVersion)
        }

        if (configManager.fakeSamplePlayer().enabled) {
            var fakeSamplePlayerCount = configManager.fakeSamplePlayer().fakePlayersCount
            if (fakeSamplePlayerCount > 400) {
                println("DO NOT SET THE FAKE PLAYER COUNT MORE THAN 400! ALREADY SET THE COUNT TO 10")
                fakeSamplePlayerCount = 10
            }
            val fakePlayers = fakeSamplePlayerGenerator.generate(fakeSamplePlayerCount)
            val samplePlayers = mutableListOf<PlayerInfo>()
            fakePlayers.forEach { player ->
                samplePlayers.add(PlayerInfo(player.name, player.uuid.toUUID()))
            }
            event.response.players.sample = samplePlayers.toTypedArray()
        }

        if (configManager.getConfig().clearSamplePlayer) {
            event.response.players.sample = arrayOf<PlayerInfo>()
        }

        if (configManager.pingPass().enabled) {
            if (pingRecordManager.exists(ip)) {
                pingRecordManager.removeRecord(ip)
            }
            pingRecordManager.addRecord(ip)
        }
    }
}