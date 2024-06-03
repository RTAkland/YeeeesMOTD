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

import cn.rtast.yeeeesmotd.entity.DecodedSkin
import cn.rtast.yeeeesmotd.entity.Skin
import cn.rtast.yeeeesmotd.entity.UsernameUUID
import cn.rtast.yeeeesmotd.gson
import java.awt.AlphaComposite
import java.awt.image.BufferedImage
import java.net.URI
import java.util.*
import javax.imageio.ImageIO


object SkinHeadUtil {

    private const val UUID_LOOKUP_URL = "https://api.mojang.com/users/profiles/minecraft/"
    private const val SKIN_SERVER_URL = "https://sessionserver.mojang.com/session/minecraft/profile/"


    private fun getSkinHead(skinUrl: String): BufferedImage {
        val url = URI(skinUrl).toURL()
        val image = ImageIO.read(url)
        var subImage = image.getSubimage(8, 8, 8, 8)

        if (isFullyTransparent(subImage)) {
            // single layer skin
            subImage = image.getSubimage(40, 8, 8, 8)
        } else {
            // double layer skin
            val hairLayer = image.getSubimage(40, 8, 8, 8)
            val combined = BufferedImage(subImage.width, subImage.height, BufferedImage.TYPE_INT_ARGB)
            val g2d = combined.createGraphics()
            g2d.drawImage(subImage, 0, 0, null)
            g2d.composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f)
            g2d.drawImage(hairLayer, 0, 0, null)  // draw hair layer on face layer
            g2d.dispose()
            subImage = combined
        }
        val zoom = scaleImage(subImage)
        return zoom
    }


    fun getSkinFavicon(skinContent: String): BufferedImage {
        val decodedSkinUrl = gson.fromJson(skinContent, DecodedSkin::class.java).textures.skin.url
        return this.getSkinHead(decodedSkinUrl)
    }

    fun getSkinFaviconWithUUID(uuid: String): BufferedImage {
        val skinResult = URI(SKIN_SERVER_URL + uuid).toURL().readText()
        val skinResultJson = gson.fromJson(skinResult, Skin::class.java)
        val decodedSkinContent =
            String(Base64.getDecoder().decode(skinResultJson.properties.first().value), Charsets.UTF_8)
        return this.getSkinFavicon(decodedSkinContent)
    }

    fun getSkinFaviconWithUsername(username: String): BufferedImage {
        val userSkinContent = URI(UUID_LOOKUP_URL + username).toURL().readText()
        val uuid = gson.fromJson(userSkinContent, UsernameUUID::class.java).id
        return this.getSkinFaviconWithUUID(uuid)
    }
}