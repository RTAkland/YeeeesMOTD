/*
 * Copyright 2024 RTAkland
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    you may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package cn.rtast.yeeeesmotd.spigot.utils

import cn.rtast.yeeeesmotd.entity.Config
import java.util.regex.Pattern

object ColorUtil {

    private val HEX_COLOR_PATTERN = Pattern.compile("<#([A-Fa-f0-9]{6})>")
    private val BOLD_PATTERN = Pattern.compile("<bold>")
    private val ITALIC_PATTERN = Pattern.compile("<italic>")
    private val UNDERLINED_PATTERN = Pattern.compile("<underlined>")
    private val STRIKETHROUGH_PATTERN = Pattern.compile("<strikethrough>")
    private val RESET_PATTERN = Pattern.compile("<reset>")

    private val COLOR_NAME_PATTERN =
        Pattern.compile("<(black|dark_blue|dark_green|dark_aqua|dark_red|dark_purple|gold|gray|dark_gray|blue|green|aqua|red|light_purple|yellow|white)>")

    private val COLOR_CODES = mapOf(
        "black" to "§0",
        "dark_blue" to "§1",
        "dark_green" to "§2",
        "dark_aqua" to "§3",
        "dark_red" to "§4",
        "dark_purple" to "§5",
        "gold" to "§6",
        "gray" to "§7",
        "dark_gray" to "§8",
        "blue" to "§9",
        "green" to "§a",
        "aqua" to "§b",
        "red" to "§c",
        "light_purple" to "§d",
        "yellow" to "§e",
        "white" to "§f"
    )

    private fun replaceHexColors(message: String): String {
        val matcher = HEX_COLOR_PATTERN.matcher(message)
        val sb = StringBuffer()
        while (matcher.find()) {
            val hexColor = matcher.group(1)
            val minecraftColor = convertHexToMinecraftColor(hexColor)
            matcher.appendReplacement(sb, minecraftColor)
        }
        matcher.appendTail(sb)
        return sb.toString()
    }

    private fun replaceBold(message: String): String {
        return BOLD_PATTERN.matcher(message).replaceAll("§l")
    }

    private fun replaceItalic(message: String): String {
        return ITALIC_PATTERN.matcher(message).replaceAll("§o")
    }

    private fun replaceUnderlined(message: String): String {
        return UNDERLINED_PATTERN.matcher(message).replaceAll("§n")
    }

    private fun replaceStrikethrough(message: String): String {
        return STRIKETHROUGH_PATTERN.matcher(message).replaceAll("§m")
    }

    private fun replaceReset(message: String): String {
        return RESET_PATTERN.matcher(message).replaceAll("§r")
    }

    private fun replaceColorNames(message: String): String {
        val matcher = COLOR_NAME_PATTERN.matcher(message)
        val sb = StringBuffer()
        while (matcher.find()) {
            val colorName = matcher.group(1)
            val minecraftColor = COLOR_CODES[colorName] ?: ""
            matcher.appendReplacement(sb, minecraftColor)
        }
        matcher.appendTail(sb)
        return sb.toString()
    }

    private fun convertMiniMessage(message: String): String {
        var result = message
        result = replaceHexColors(result)
        result = replaceBold(result)
        result = replaceItalic(result)
        result = replaceUnderlined(result)
        result = replaceStrikethrough(result)
        result = replaceReset(result)
        result = replaceColorNames(result)
        return result
    }

    fun convertDescription(description: Config.Description): Config.Description {
        val line1 = description.line1
        val line2 = description.line2
        val convertedLine1 = this.convertMiniMessage(line1)
        val convertedLine2 = this.convertMiniMessage(line2)
        return Config.Description(convertedLine1, convertedLine2)
    }

    fun convertHexToMinecraftColor(hex: String): String {
        val minecraftColor = StringBuilder("§x")
        hex.forEach { char ->
            minecraftColor.append("§").append(char)
        }
        return minecraftColor.toString()
    }
}
