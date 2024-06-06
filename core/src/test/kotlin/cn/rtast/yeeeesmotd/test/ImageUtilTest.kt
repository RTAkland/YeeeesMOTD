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


package cn.rtast.yeeeesmotd.test

import cn.rtast.yeeeesmotd.DEFAULT_ICON
import cn.rtast.yeeeesmotd.test.util.toBase64ByteArray
import cn.rtast.yeeeesmotd.utils.isFullyTransparent
import cn.rtast.yeeeesmotd.utils.scaleImage
import cn.rtast.yeeeesmotd.utils.toBufferedImage
import cn.rtast.yeeeesmotd.utils.toByteArray
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.Test
import kotlin.test.assertFalse

class ImageUtilTest {

    private val originImage = DEFAULT_ICON.toBase64ByteArray()

    @Test
    fun testScaleImage() {
        assertDoesNotThrow {
            originImage.toBufferedImage().scaleImage(64 to 64)
        }
    }

    @Test
    fun testIsFullyTransparent() {
        assertFalse(originImage.toBufferedImage().isFullyTransparent())
    }

    @Test
    fun testByteArrayToBufferedImage() {
        assertDoesNotThrow {
            originImage.toBufferedImage()
        }
    }

    @Test
    fun testBufferedImageToByteArray() {
        assertDoesNotThrow {
            originImage.toBufferedImage().toByteArray()
        }
    }
}