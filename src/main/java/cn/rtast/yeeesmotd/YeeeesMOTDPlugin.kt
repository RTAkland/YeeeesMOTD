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
package cn.rtast.yeeesmotd

import cn.rtast.yeeesmotd.command.YesMOTDCommand.createCommand
import cn.rtast.yeeesmotd.listeners.LoginEventListener
import cn.rtast.yeeesmotd.listeners.ProxyPingEventListener
import cn.rtast.yeeesmotd.listeners.ServerConnectedEventListener
import cn.rtast.yeeesmotd.utils.HitokotoUtil
import cn.rtast.yeeesmotd.utils.file.ConfigManager
import cn.rtast.yeeesmotd.utils.file.FaviconManager
import cn.rtast.yeeesmotd.utils.file.PingRecordManager
import cn.rtast.yeeesmotd.utils.file.SkinHeadManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.inject.Inject
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.proxy.ProxyServer
import net.kyori.adventure.text.minimessage.MiniMessage
import org.slf4j.Logger

class YeeeesMOTDPlugin @Inject constructor(private val logger: Logger, private val proxy: ProxyServer) {

    companion object {
        val gson: Gson = GsonBuilder().disableHtmlEscaping().create()
        val miniMessage: MiniMessage = MiniMessage.miniMessage()

        val faviconManager: FaviconManager = FaviconManager()
        val skinHeadManager: SkinHeadManager = SkinHeadManager()
        val pingRecordManager: PingRecordManager = PingRecordManager()
        val configManager: ConfigManager = ConfigManager()
        val hitokotoUtil: HitokotoUtil = HitokotoUtil()
    }

    init {
        faviconManager.setValidIcons()
        logger.debug("YeeeesMOTD初始化完成")
    }

    @Subscribe
    fun onProxyInitialization(event: ProxyInitializeEvent) {
        proxy.eventManager.register(this, ProxyPingEventListener())
        proxy.eventManager.register(this, ServerConnectedEventListener(proxy))
        proxy.eventManager.register(this, LoginEventListener())

        val commandManager = proxy.commandManager
        val commandMeta = commandManager.metaBuilder("yesmotd")
            .plugin(this)
            .build()
        val yesMOTDCommand = createCommand()
        commandManager.register(commandMeta, yesMOTDCommand)
    }
}