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


package cn.rtast.yeeeesmotd

import cn.rtast.yeeeesmotd.entity.Config

const val SCHEMA_VERSION = 1.0

const val ROOT_PATH = "./plugins/YeeeesMotd/"

const val DEFAULT_ICON = ""

const val PING_FIRST_TEXT = "Please ping the server first! / 请先在服务器列表Ping一次服务器"

const val RE_PING_TEXT = "Please ping the server again! / 请重新Ping一次服务器"

const val DEFAULT_PING_INTERVAL = 120

const val HITOKOTO_SENTENCE_URL = "https://static.rtast.cn/hitokoto"

val BUILD_IN_DESCRIPTIONS = listOf(
    "\$player <#A020F0>是吧? 还不赶快进来\n<#A020F0>不然有你好果子吃的!",
    "<bold><#EE82EE>这是 \$player <bold><#EE82EE>的专属服务器呀~",
    "<bold><#CD96CD>逸一时误一世!\n\$player <bold><#CD96CD>赶快给我进来!"
)

val DEFAULT_DESCRIPTIONS = mutableListOf(
    Config.Description(
        "<yellow><bold>YeeeesMOTD",
        "<light_purple><bold>Powered by RTAkland: https://github.com/RTAkland"
    ),
    Config.Description("<#00ff00><bold>DangoTown 团子小镇 生电服务器欢迎你", "<yellow><bold>https://dgtmc.top"),
    Config.Description("<#00FFFF><bold>团子小镇是一个历史悠久的服务器", "<#00FFFF><bold>服务器于2016年开服至今")
)
