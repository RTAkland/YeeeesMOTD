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
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

fun zoomTo64(image: ByteArray): ByteArray {
    val originImage = ImageIO.read(image.inputStream())
    val newWidth = 64
    val newHeight = 64
    val scaledImage = BufferedImage(newWidth, newHeight, originImage.type)
    val graphics2d = scaledImage.createGraphics()
    graphics2d.drawImage(originImage, 0, 0, newWidth, newHeight, null)
    graphics2d.dispose()
    val outputStream = ByteArrayOutputStream()
    ImageIO.write(scaledImage, "png", outputStream)
    return outputStream.toByteArray()
}