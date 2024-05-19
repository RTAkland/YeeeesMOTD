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

import cn.rtast.yeeeesmotd.IYeeeesMOTD.Companion.PING_AGAIN_TEXT
import cn.rtast.yeeeesmotd.IYeeeesMOTD.Companion.PING_FIRST_TEXT
import cn.rtast.yeeeesmotd.IYeeeesMOTD.Companion.configManager
import cn.rtast.yeeeesmotd.IYeeeesMOTD.Companion.pingRecordManager
import cn.rtast.yeeeesmotd.IYeeeesMOTD.Companion.skinHeadManager
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.connection.LoginEvent
import com.velocitypowered.api.event.player.ServerConnectedEvent
import com.velocitypowered.api.proxy.ProxyServer
import com.velocitypowered.api.util.GameProfile
import net.kyori.adventure.text.Component
import java.time.Instant
import java.util.*

class LoginEventListener(private val proxy: ProxyServer) {

    private fun getTextureContent(profile: GameProfile): String {
        val texturesBase64 = profile.properties.toString().split(",")[1].split("=")[1].replace("'", "")
        return String(Base64.getDecoder().decode(texturesBase64), Charsets.UTF_8)
    }

    @Subscribe
    fun onLogin(event: LoginEvent) {
        if (configManager.pingPass().enabled) {
            val ip = event.player.remoteAddress.hostName
            if (pingRecordManager.exists(ip)) {
                val record = pingRecordManager.getRecord(ip)
                val currentTimestamp = Instant.now().epochSecond
                val interval = configManager.pingPass().interval
                if (currentTimestamp - record.timestamp > interval) {
                    event.player.disconnect(Component.text(PING_AGAIN_TEXT))
                    pingRecordManager.removeRecord(ip)
                }
            } else {
                event.player.disconnect(Component.text(PING_FIRST_TEXT))
            }
        }
    }

    @Subscribe
    fun onServerConnected(event: ServerConnectedEvent) {
        val ip = event.player.remoteAddress.hostName
        val name = event.player.username
        val uuid = event.player.uniqueId.toString()

        if (proxy.configuration.isOnlineMode) {
            val textureContent = this.getTextureContent(event.player.gameProfile)
            if (!skinHeadManager.exists(ip)) {
                skinHeadManager.addHead(name, uuid, ip, textureContent)
            } else {
                skinHeadManager.updateHead(name, uuid, ip, textureContent)
            }
        }

        if (configManager.pingPass().enabled) {
            if (pingRecordManager.exists(ip)) {
                pingRecordManager.removeRecord(ip)
            }
        }
    }
}
