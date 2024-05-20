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


package cn.rtast.yeeeesmotd.bungee.command

import cn.rtast.yeeeesmotd.IYeeeesMOTD
import cn.rtast.yeeeesmotd.IYeeeesMOTD.Companion.configManager
import cn.rtast.yeeeesmotd.IYeeeesMOTD.Companion.faviconManager
import cn.rtast.yeeeesmotd.IYeeeesMOTD.Companion.skinHeadManager
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.plugin.Command


class YeeeesMOTDCommand(name: String) : Command(name) {

    override fun execute(sender: CommandSender, args: Array<out String>) {
        if (!sender.hasPermission("yeeeesmotd.admin")) {
            return
        }
        if (args.firstOrNull() == "reload") {
            faviconManager.setValidIcons()
            val config = configManager.getConfig()
            IYeeeesMOTD.PING_FIRST_TEXT = config.pingPass.pingFirstText
            IYeeeesMOTD.PING_AGAIN_TEXT = config.pingPass.pingAgainText
            sender.sendMessage(TextComponent("Successfully reloaded"))
        } else if (args.firstOrNull() == "clear") {
            skinHeadManager.clear()
            sender.sendMessage(TextComponent("Successfully cleared"))
        }
    }
}