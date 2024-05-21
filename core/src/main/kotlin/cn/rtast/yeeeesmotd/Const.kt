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
import com.google.gson.Gson
import com.google.gson.GsonBuilder

val gson: Gson = GsonBuilder().disableHtmlEscaping().create()

const val SCHEMA_VERSION = 1.0

const val ROOT_PATH = "./plugins/YeeeesMotd/"

const val PING_FIRST_TEXT = "Please ping the server first! / 请先在服务器列表Ping一次服务器"

const val RE_PING_TEXT = "Please ping the server again! / 请重新Ping一次服务器"

const val DEFAULT_PING_INTERVAL = 120

const val HITOKOTO_SENTENCE_URL = "https://static.rtast.cn/hitokoto"

const val SKIN_SERVER_URL = "https://sessionserver.mojang.com/session/minecraft/profile/"

const val UUID_LOOKUP_URL = "https://api.mojang.com/users/profiles/minecraft/"

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

const val DEFAULT_ICON =
    "iVBORw0KGgoAAAANSUhEUgAAAEAAAABACAYAAACqaXHeAAABtUlEQVR4" +
            "Xu3QMWoeQRBE4bmTYp/O13JgcOBQoECpwUJIlgQW8gF+xf4G" +
            "thnXisVsP3hJM1Mz1eNSMMb4UFPMW9ZAmS7sbIp5yxoo04Wd" +
            "TTFvWQNlurCzKeYta6BMF3Y2xbxlDZTpws6mmLesgTJd2NkU" +
            "85Y1UKYLO5ti3j84Df6ywvOaYp6mDAO1wvOaYp6mDAO1wvOa" +
            "Yp6mDAO1wvOaYp6mDAO1wvOaYp6mDAO1wvOaYp6mDAO1wvOa" +
            "Yp6mDAO1wvOaYp6m5An/Ob0AB2ejF+DgbPQCHJyNXsDvX9eX" +
            "LV/uv2/69nK7acrz3bdNn35+3fT1+WbTXoCF1cJqYU2xsFpY" +
            "Lay9AAurhdXCmmJhtbBaWHsBFlYLq4U1xcJqYbWw9gJ8UL48" +
            "PG56NP5HK3oBDsRAPRr/oxW9AAdioB6N/9GKXoADMVCPxv9o" +
            "RS/AgRioR+N/tKIX4EAM1KPxP1rRC3AgBurR+B+t6AU4EAP1" +
            "aPyPVvQCHJyNXoCDs9ELcHA2egEOzkYv4M/nH5ctU24+XUWm" +
            "2Ed7AQ40xUKrpthHewEONMVCq6bYR3sBDjTFQqum2EdPv4B3" +
            "trmZei1ahp0AAAAASUVORK5CYII="

