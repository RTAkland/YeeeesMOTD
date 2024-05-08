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


package cn.rtast.yeeeesmotd.utils.file

import cn.rtast.yeeeesmotd.YeeeesMOTD
import cn.rtast.yeeeesmotd.entity.file.Head
import cn.rtast.yeeeesmotd.utils.SkinHeadUtil
import com.google.gson.reflect.TypeToken
import java.util.*
import kotlin.concurrent.thread

class SkinHeadManager : IJsonManager<MutableList<Head>>("heads.json", mutableListOf()) {

    private fun read(): MutableList<Head> {
        val str = this.file.readText()
        val type = object : TypeToken<MutableList<Head>>() {}.type
        return YeeeesMOTD.gson.fromJson(str, type)
    }

    private fun write(data: MutableList<Head>) {
        val serData = YeeeesMOTD.gson.toJson(data)
        this.file.writeText(serData)
    }

    fun addHead(name: String, uuid: String, ip: String, skinContent: String) {
        thread {
            val allHeads = this.read()
            val skin = SkinHeadUtil.getSkinFavicon(skinContent)
            val encodedSkinImage = String(Base64.getEncoder().encode(skin), Charsets.UTF_8)
            allHeads.add(Head(name, uuid, encodedSkinImage, ip))
            this.write(allHeads)
        }
    }

    fun getHead(ip: String): Head {
        val allHeads = this.read()
        return allHeads.find { it.ip == ip } ?: Head("", "", "", "")
    }

    fun exists(ip: String): Boolean {
        val allHeads = this.read()
        return allHeads.any { it.ip == ip }
    }

    fun clear() {
        val allHeads = this.read()
        allHeads.clear()
        this.write(allHeads)
    }
}