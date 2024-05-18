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


package cn.rtast.yeeeesmotd.velocity.command

import cn.rtast.yeeeesmotd.velocity.YeeeesMOTDPlugin.Companion.configManager
import cn.rtast.yeeeesmotd.velocity.YeeeesMOTDPlugin.Companion.faviconManager
import cn.rtast.yeeeesmotd.velocity.YeeeesMOTDPlugin.Companion.skinHeadManager
import cn.rtast.yeeeesmotd.velocity.listeners.LoginEventListener
import com.velocitypowered.api.command.BrigadierCommand
import net.kyori.adventure.text.Component

object YesMOTDCommand {

    fun createCommand(): BrigadierCommand {
        val node = BrigadierCommand.literalArgumentBuilder("yesmotd")
            .requires { it.hasPermission("yesmotd.player") }
            .then(
                BrigadierCommand.literalArgumentBuilder("reload")
                    .executes {
                        faviconManager.setValidIcons()
                        val config = configManager.getConfig()
                        LoginEventListener.PING_FIRST_TEXT = config.pingPass.pingFirstText
                        LoginEventListener.PING_AGAIN_TEXT = config.pingPass.pingAgainText
                        it.source.sendMessage(Component.text("Successfully reloaded"))
                        return@executes 1
                    }
            )
            .then(
                BrigadierCommand.literalArgumentBuilder("clear")
                    .executes {
                        skinHeadManager.clear()
                        it.source.sendMessage(Component.text("Successfully cleared"))
                        return@executes 1
                    }
            )
            .executes {
                it.source.sendMessage(Component.text("No valid args found. Do /yesmotd clear or /yesmotd reload"))
                return@executes 1
            }
        return BrigadierCommand(node.build())
    }
}