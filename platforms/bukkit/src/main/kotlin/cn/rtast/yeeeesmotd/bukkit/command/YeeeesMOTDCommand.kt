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


package cn.rtast.yeeeesmotd.bukkit.command

import cn.rtast.yeeeesmotd.bukkit.YeeeesMOTDPlugin.Companion.configManager
import cn.rtast.yeeeesmotd.bukkit.YeeeesMOTDPlugin.Companion.faviconManager
import cn.rtast.yeeeesmotd.bukkit.YeeeesMOTDPlugin.Companion.skinHeadManager
import cn.rtast.yeeeesmotd.bukkit.events.PlayerJoinEventListener
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class YeeeesMOTDCommand: CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (!sender.hasPermission("yeeeesmotd.admin")) {
            sender.sendMessage("You don't have permission to use this command!")
        }

        if (command.label == "yesmotd:reload") {
            faviconManager.setValidIcons()
            val config = configManager.getConfig()
            PlayerJoinEventListener.PING_FIRST_TEXT = config.pingPass.pingFirstText
            PlayerJoinEventListener.PING_AGAIN_TEXT = config.pingPass.pingAgainText
            sender.sendMessage("Successfully reload")
        }

        if (command.label == "yesmotd:clear") {
            skinHeadManager.clear()
            sender.sendMessage("Successfully clear")
        }

        return true
    }
}