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

import cn.rtast.yeeeesmotd.entity.Head
import cn.rtast.yeeeesmotd.gson
import cn.rtast.yeeeesmotd.utils.SkinHeadUtil
import cn.rtast.yeeeesmotd.utils.bufferedImageToByteArray
import com.google.gson.reflect.TypeToken
import java.util.*
import kotlin.concurrent.thread


class SkinHeadManager : IJsonManager<MutableList<Head>>("heads.json", mutableListOf()) {

    private fun read(): MutableList<Head> {
        val str = this.file.readText()
        val type = object : TypeToken<MutableList<Head>>() {}.type
        return gson.fromJson(str, type)
    }

    private fun write(data: MutableList<Head>) {
        val serData = gson.toJson(data)
        this.file.writeText(serData)
    }

    fun addHead(name: String, uuid: String, ip: String, textureContent: String) {
        thread {
            val allHeads = this.read()
            val skin = SkinHeadUtil.getSkinFavicon(textureContent)
            val encodedSkinImage =
                String(Base64.getEncoder().encode(bufferedImageToByteArray(skin)), Charsets.UTF_8)
            allHeads.add(Head(name, uuid, encodedSkinImage, ip))
            this.write(allHeads)
        }
    }

    fun addHead(name: String, uuid: String, ip: String) {
        thread {
            val allHeads = this.read()
            val skin = SkinHeadUtil.getSkinFaviconWithUUID(uuid)
            val encodedSkinImage =
                String(Base64.getEncoder().encode(bufferedImageToByteArray(skin)), Charsets.UTF_8)
            allHeads.add(Head(name, uuid, encodedSkinImage, ip))
            this.write(allHeads)
        }
    }

    fun updateHead(name: String, uuid: String, ip: String, textureContent: String) {
        thread {
            val allHeads = this.read()
            val skin = SkinHeadUtil.getSkinFavicon(textureContent)
            val encodedSkinImage =
                String(Base64.getEncoder().encode(bufferedImageToByteArray(skin)), Charsets.UTF_8)
            allHeads.removeAll { it.ip == ip }
            allHeads.add(Head(name, uuid, encodedSkinImage, ip))
            this.write(allHeads)
        }
    }

    fun updateHead(name: String, uuid: String, ip: String) {
        thread {
            val allHeads = this.read()
            val skin = SkinHeadUtil.getSkinFaviconWithUUID(uuid)
            val encodedSkinImage =
                String(Base64.getEncoder().encode(bufferedImageToByteArray(skin)), Charsets.UTF_8)
            allHeads.removeAll { it.ip == ip }
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