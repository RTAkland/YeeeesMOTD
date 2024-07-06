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


package cn.rtast.yeeeesmotd.spigot.listeners

import cn.rtast.yeeeesmotd.IYeeeesMOTD.Companion.configManager
import cn.rtast.yeeeesmotd.IYeeeesMOTD.Companion.defaultIcon
import cn.rtast.yeeeesmotd.IYeeeesMOTD.Companion.faviconManager
import cn.rtast.yeeeesmotd.IYeeeesMOTD.Companion.hitokotoManager
import cn.rtast.yeeeesmotd.IYeeeesMOTD.Companion.pingRecordManager
import cn.rtast.yeeeesmotd.IYeeeesMOTD.Companion.skinHeadManager
import cn.rtast.yeeeesmotd.entity.Config
import cn.rtast.yeeeesmotd.spigot.utils.ColorUtil
import cn.rtast.yeeeesmotd.utils.nextBoolean
import org.bukkit.Server
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.server.ServerListPingEvent
import java.io.ByteArrayInputStream
import java.util.*
import javax.imageio.ImageIO
import kotlin.random.Random

class ServerListPingEventListener(private val server: Server) : Listener {

    @EventHandler
    fun onServerListPingEvent(event: ServerListPingEvent) {
        val ip = event.address.hostName

        var favicon = server.loadServerIcon(defaultIcon)
        val showHead = Random.nextBoolean()

        val randomDescription = configManager.getRandomDescription()
        val finalDescription = StringBuilder()

        if (randomDescription == null) {
            finalDescription.append(event.motd)
        } else {
            val converted = ColorUtil.convertDescription(randomDescription)
            finalDescription.append("${converted.line1}\n${converted.line2}")
        }

        val p = configManager.hitokoto().probability
        val showHitokoto = nextBoolean(p)
        if (configManager.hitokoto().enabled && showHitokoto) {
            val hitokoto = hitokotoManager.getSentence()
            val color = configManager.hitokoto().color
            val convertedColor = ColorUtil.convertHexToMinecraftColor(color)
            finalDescription.clear()
                .append("$convertedColor${hitokoto.line1}\n$convertedColor${hitokoto.line2}")
        }


        if (showHead && skinHeadManager.exists(ip) && configManager.ipFingerprint().enabled) {
            val userData = skinHeadManager.getHead(ip)
            val decodedHead = Base64.getDecoder().decode(userData.head)
            val bufferedHead = ImageIO.read(ByteArrayInputStream(decodedHead))
            favicon = server.loadServerIcon(bufferedHead)

            val randomBuildInDesc =
                configManager.getRandomBuildInDescription().split("\$player")
            val converted = ColorUtil.convertDescription(Config.Description(randomBuildInDesc[0], randomBuildInDesc[1]))
            finalDescription.clear()
                .append(converted.line1)
                .append("§x§E§E§8§2§E§E${userData.name}")
                .append(converted.line2)
        } else {
            val randomIcon = faviconManager.getRandomIcon()
            if (randomIcon != null) {
                favicon = server.loadServerIcon(randomIcon)
            }
        }

        if (configManager.pingList().maximumPlayerEnabled) {
            event.maxPlayers = configManager.pingList().maximumPlayer
        }
        event.motd = finalDescription.toString()
        event.setServerIcon(favicon)

        if (configManager.pingPass().enabled) {
            if (pingRecordManager.exists(ip)) {
                pingRecordManager.removeRecord(ip)
            }
            pingRecordManager.addRecord(ip)
        }
    }
}