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


package cn.rtast.yeeeesmotd.command

import cn.rtast.yeeeesmotd.YeeeesMOTD
import com.mojang.brigadier.CommandDispatcher
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.command.CommandRegistryAccess
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.Text
import net.minecraft.util.Formatting


class ReloadCommand : CommandRegistrationCallback {

    override fun register(
        dispatcher: CommandDispatcher<ServerCommandSource>,
        registryAccess: CommandRegistryAccess,
        environment: CommandManager.RegistrationEnvironment,
    ) {
        dispatcher.register(
            CommandManager.literal("yesmotd").requires { it.hasPermissionLevel(2) }
                .then(CommandManager.literal("reload")
                    .executes {
                        YeeeesMOTD.iconManager.setValidIcons()
                        it.source.sendMessage(
                            Text.translatable("reload.success").styled { style -> style.withColor(Formatting.YELLOW) })
                        return@executes 1
                    })
                .then(CommandManager.literal("clear")
                    .executes {
                        YeeeesMOTD.skinHeadManagerV2.clear()
                        return@executes 1
                    })
        )
    }
}