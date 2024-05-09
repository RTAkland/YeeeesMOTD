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


package cn.rtast.yeeesmotd.utils

import java.awt.image.BufferedImage

fun zoomTo64(image: BufferedImage): BufferedImage {
    val newWidth = 64
    val newHeight = 64
    val scaledImage = BufferedImage(newWidth, newHeight, image.type)
    val graphics2d = scaledImage.createGraphics()
    graphics2d.drawImage(image, 0, 0, newWidth, newHeight, null)
    graphics2d.dispose()
    return scaledImage
}