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


package cn.rtast.yeeeesmotd.paper.listeners

import cn.rtast.yeeeesmotd.IYeeeesMOTD.Companion.PING_AGAIN_TEXT
import cn.rtast.yeeeesmotd.IYeeeesMOTD.Companion.PING_FIRST_TEXT
import cn.rtast.yeeeesmotd.IYeeeesMOTD.Companion.configManager
import cn.rtast.yeeeesmotd.IYeeeesMOTD.Companion.pingRecordManager
import cn.rtast.yeeeesmotd.IYeeeesMOTD.Companion.skinHeadManager
import com.destroystokyo.paper.profile.ProfileProperty
import net.kyori.adventure.text.Component
import org.bukkit.Server
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import java.time.Instant
import java.util.*

class PlayerJoinEventListener(private val server: Server) : Listener {

    private fun getTextureContent(profiles: Set<ProfileProperty>): String {
        val value = StringBuilder()
        for (profile in profiles) {
            value.append(profile.value)
        }
        return String(Base64.getDecoder().decode(value.toString()), Charsets.UTF_8)
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val ip = event.player.address.hostName
        val name = event.player.name
        val uuid = event.player.uniqueId.toString()

        if (server.onlineMode) {
            val textureContent = this.getTextureContent(event.player.playerProfile.properties)
            if (!skinHeadManager.exists(ip)) {
                skinHeadManager.addHead(name, uuid, ip, textureContent)
            } else {
                skinHeadManager.updateHead(name, uuid, ip, textureContent)
            }
        }

        if (configManager.pingPass().enabled) {
            if (pingRecordManager.exists(ip)) {
                val record = pingRecordManager.getRecord(ip)
                val currentTimestamp = Instant.now().epochSecond
                val interval = configManager.pingPass().interval
                if (currentTimestamp - record.timestamp > interval) {
                    event.player.kick(Component.text(PING_AGAIN_TEXT))
                    pingRecordManager.removeRecord(ip)
                }
            } else {
                event.player.kick(Component.text(PING_FIRST_TEXT))
            }
        }

        if (configManager.pingPass().enabled) {
            if (pingRecordManager.exists(ip)) {
                pingRecordManager.removeRecord(ip)
            }
        }
    }
}