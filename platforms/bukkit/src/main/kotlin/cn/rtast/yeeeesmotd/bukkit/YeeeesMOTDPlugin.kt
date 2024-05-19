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


package cn.rtast.yeeeesmotd.bukkit

import cn.rtast.yeeeesmotd.IYeeeesMOTD
import cn.rtast.yeeeesmotd.IYeeeesMOTD.Companion.faviconManager
import cn.rtast.yeeeesmotd.bukkit.command.YeeeesMOTDCommand
import cn.rtast.yeeeesmotd.bukkit.listeners.PlayerJoinEventListener
import cn.rtast.yeeeesmotd.bukkit.listeners.ServerListPingEventListener
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.plugin.java.JavaPlugin

class YeeeesMOTDPlugin : JavaPlugin(), IYeeeesMOTD {

    companion object {
        val miniMessage: MiniMessage = MiniMessage.miniMessage()
    }

    init {
        faviconManager.setValidIcons()
    }

    override fun onEnable() {
        server.pluginManager.registerEvents(ServerListPingEventListener(server), this)
        server.pluginManager.registerEvents(PlayerJoinEventListener(server), this)

        this.getCommand("yeeeesmotd")?.setExecutor(YeeeesMOTDCommand())
    }
}
