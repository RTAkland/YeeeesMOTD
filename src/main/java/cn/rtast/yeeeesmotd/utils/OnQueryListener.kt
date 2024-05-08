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


package cn.rtast.yeeeesmotd.utils

import cn.rtast.yeeeesmotd.YeeeesMOTD.Companion.descriptionManager
import cn.rtast.yeeeesmotd.YeeeesMOTD.Companion.iconManager
import cn.rtast.yeeeesmotd.YeeeesMOTD.Companion.skinHeadManagerV2
import net.minecraft.server.ServerMetadata
import net.minecraft.server.ServerMetadata.Favicon
import net.minecraft.server.ServerMetadata.Players
import net.minecraft.text.Style
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import java.util.*

fun onQuery(metadata: ServerMetadata, ip: String): ServerMetadata {
    val showHead = Random().nextBoolean()
    var favicon: Optional<Favicon> = metadata.favicon()
    val randomDescription = descriptionManager.randomDescription()
    var description = Text.empty()
    if (randomDescription == null) {
        description = metadata.description().copy()
    } else {
        description.append(randomDescription.line1 + "\n")
            .append(randomDescription.line2)
            .styled { it.withColor(randomDescription.color.toInt()) }
    }

    if (showHead && skinHeadManagerV2.exists(ip)) {
        val userData = skinHeadManagerV2.getHead(ip)
        val skin = Base64.getDecoder().decode(userData.head)
        val name = userData.name
        favicon = Optional.of(Favicon(skin))
        val randomBuildInDesc = descriptionManager.randomBuildInDesc().replace("\$player", name)
        description = Text.literal(randomBuildInDesc).styled { style: Style ->
            style.withFormatting(
                Formatting.GREEN
            )
        }
    } else {
        val randomIcon = iconManager.getRandomIcon()
        if (randomIcon != null) {
            favicon = Optional.of(Favicon(randomIcon))
        }
    }

    return ServerMetadata(
        description,
        Optional.of(Players(-1, -1, ArrayList())),
        metadata.version(),
        favicon,
        metadata.secureChatEnforced()
    )
}