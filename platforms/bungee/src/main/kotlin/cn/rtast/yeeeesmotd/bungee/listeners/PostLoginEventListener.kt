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

import cn.rtast.yeeeesmotd.IYeeeesMOTD.Companion.PING_AGAIN_TEXT
import cn.rtast.yeeeesmotd.IYeeeesMOTD.Companion.PING_FIRST_TEXT
import cn.rtast.yeeeesmotd.IYeeeesMOTD.Companion.configManager
import cn.rtast.yeeeesmotd.IYeeeesMOTD.Companion.pingRecordManager
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer
import net.md_5.bungee.api.event.PostLoginEvent
import net.md_5.bungee.api.event.ServerConnectedEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler
import java.time.Instant

class PostLoginEventListener : Listener {

    @EventHandler
    fun onPostLoginEvent(event: PostLoginEvent) {
        val ip = event.player.socketAddress.toString().split(":").first().replace("/", "")

        if (configManager.pingPass().enabled) {
            if (pingRecordManager.exists(ip)) {
                val record = pingRecordManager.getRecord(ip)
                val currentTimestamp = Instant.now().epochSecond
                val interval = configManager.pingPass().interval
                if (currentTimestamp - record.timestamp > interval) {
                    event.player.disconnect(
                        BungeeComponentSerializer.get().serialize(Component.text(PING_AGAIN_TEXT)).first()
                    )
                    pingRecordManager.removeRecord(ip)
                }
            } else {
                event.player.disconnect(
                    BungeeComponentSerializer.get().serialize(Component.text(PING_FIRST_TEXT)).first()
                )
            }
        }
    }

    @EventHandler
    fun onServerConnectedEvent(event: ServerConnectedEvent) {
        val ip = event.player.socketAddress.toString()
        if (configManager.pingPass().enabled) {
            if (pingRecordManager.exists(ip)) {
                pingRecordManager.removeRecord(ip)
            }
        }
    }
}