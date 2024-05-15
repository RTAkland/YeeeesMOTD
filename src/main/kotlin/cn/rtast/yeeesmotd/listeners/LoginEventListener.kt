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
import com.velocitypowered.api.event.connection.LoginEvent
import net.kyori.adventure.text.Component
import java.time.Instant

class LoginEventListener {

    companion object {
        var PING_FIRST_TEXT = YeeeesMOTDPlugin.configManager.pingPass().pingFirstText
        var PING_AGAIN_TEXT = YeeeesMOTDPlugin.configManager.pingPass().pingAgainText
    }

    @Subscribe
    fun onLogin(event: LoginEvent) {
        if (YeeeesMOTDPlugin.configManager.pingPass().enabled) {
            val ip = event.player.remoteAddress.hostName
            if (YeeeesMOTDPlugin.pingRecordManager.exists(ip)) {
                val record = YeeeesMOTDPlugin.pingRecordManager.getRecord(ip)
                val currentTimestamp = Instant.now().epochSecond
                val interval = YeeeesMOTDPlugin.configManager.pingPass().interval
                if (currentTimestamp - record.timestamp > interval) {
                    event.player.disconnect(Component.text(PING_AGAIN_TEXT))
                    YeeeesMOTDPlugin.pingRecordManager.removeRecord(ip)
                }
            } else {
                event.player.disconnect(Component.text(PING_FIRST_TEXT))
            }
        }
    }
}
