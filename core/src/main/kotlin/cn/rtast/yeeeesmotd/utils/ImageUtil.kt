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

import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

fun BufferedImage.isFullyTransparent(): Boolean {
    val width = this.width
    val height = this.height
    for (y in 0 until height) {
        for (x in 0 until width) {
            val pixel = this.getRGB(x, y)
            if ((pixel shr 24) != 0x00) {
                return false
            }
        }
    }
    return true
}

fun BufferedImage.scaleImage(size: Pair<Int, Int>): BufferedImage {
    val scaledImage = BufferedImage(size.first, size.second, this.type)
    val graphics2d = scaledImage.createGraphics()
    graphics2d.drawImage(this, 0, 0, size.first, size.second, null)
    graphics2d.dispose()
    return scaledImage
}

fun ByteArray.toBufferedImage(): BufferedImage {
    ByteArrayInputStream(this).use { inputStream ->
        return ImageIO.read(inputStream)
    }
}

fun BufferedImage.toByteArray(): ByteArray {
    ByteArrayOutputStream().use { outputStream ->
        ImageIO.write(this, "png", outputStream)
        return outputStream.toByteArray()
    }
}
