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

import cn.rtast.yeeeesmotd.YeeeesMOTD
import cn.rtast.yeeeesmotd.entity.DecodedSkin
import java.awt.AlphaComposite
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.net.URI
import javax.imageio.ImageIO


object SkinHeadUtil {

    private fun isFullyTransparent(image: BufferedImage): Boolean {
        val width = image.width
        val height = image.height
        for (y in 0 until height) {
            for (x in 0 until width) {
                val pixel = image.getRGB(x, y)
                if ((pixel shr 24) != 0x00) {
                    return false
                }
            }
        }
        return true
    }

    private fun getSkinHead(skinUrl: String): ByteArray {
        val url = URI(skinUrl).toURL()
        val image = ImageIO.read(url)
        var subImage = image.getSubimage(8, 8, 8, 8)

        if (isFullyTransparent(subImage)) {
            // single layer skin
            subImage = image.getSubimage(40, 8, 8, 8)
        } else {
            // multi layer skin
            val hairLayer = image.getSubimage(40, 8, 8, 8)
            val combined = BufferedImage(subImage.width, subImage.height, BufferedImage.TYPE_INT_ARGB)
            val g2d = combined.createGraphics()
            g2d.drawImage(subImage, 0, 0, null);
            g2d.composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f)
            g2d.drawImage(hairLayer, 0, 0, null)
            g2d.dispose()
            subImage = combined
        }
        val outputStream = ByteArrayOutputStream()
        ImageIO.write(subImage, "png", outputStream)
        val headByteArray = outputStream.toByteArray()
        val zoom = zoomTo64(headByteArray)
        return zoom
    }


    fun getSkinFavicon(skinContent: String): ByteArray {
        val decodedSkinUrl = YeeeesMOTD.gson.fromJson(skinContent, DecodedSkin::class.java).textures.skin.url
        return getSkinHead(decodedSkinUrl)
    }
}
