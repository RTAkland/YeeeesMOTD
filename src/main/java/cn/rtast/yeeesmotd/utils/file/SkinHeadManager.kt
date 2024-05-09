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


package cn.rtast.yeeesmotd.utils.file

import cn.rtast.yeeesmotd.YeeesMOTDPlugin
import cn.rtast.yeeesmotd.entity.file.Head
import cn.rtast.yeeesmotd.utils.SkinHeadUtil
import com.google.gson.reflect.TypeToken
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.util.*
import javax.imageio.ImageIO
import kotlin.concurrent.thread


class SkinHeadManager : IJsonManager<MutableList<Head>>("heads.json", mutableListOf()) {

    private fun read(): MutableList<Head> {
        val str = this.file.readText()
        val type = object : TypeToken<MutableList<Head>>() {}.type
        return YeeesMOTDPlugin.gson.fromJson(str, type)
    }

    private fun write(data: MutableList<Head>) {
        val serData = YeeesMOTDPlugin.gson.toJson(data)
        this.file.writeText(serData)
    }

    private fun bufferedImageToByteArray(image: BufferedImage?): ByteArray {
        val baos = ByteArrayOutputStream()
        ImageIO.write(image, "png", baos) // 这里选择 PNG 格式，你可以根据需要选择其他格式
        val byteArray = baos.toByteArray()
        baos.close()
        return byteArray
    }

    fun addHead(name: String, uuid: String, ip: String, textureContent: String) {
        thread {
            val allHeads = this.read()
            val skin = SkinHeadUtil.getSkinFavicon(textureContent)
            val encodedSkinImage =
                String(Base64.getEncoder().encode(this.bufferedImageToByteArray(skin)), Charsets.UTF_8)
            allHeads.add(Head(name, uuid, encodedSkinImage, ip))
            this.write(allHeads)
        }
    }

    fun updateHead(name: String, uuid: String, ip: String, textureContent: String) {
        thread {
            val allHeads = this.read()
            val skin = SkinHeadUtil.getSkinFavicon(textureContent)
            val encodedSkinImage =
                String(Base64.getEncoder().encode(this.bufferedImageToByteArray(skin)), Charsets.UTF_8)
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