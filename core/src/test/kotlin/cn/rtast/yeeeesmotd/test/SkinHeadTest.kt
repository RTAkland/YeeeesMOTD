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

import cn.rtast.yeeeesmotd.utils.SkinHeadUtil
import kotlin.test.Test
import kotlin.test.assertNotNull

class SkinHeadTest {

    private val username = "RTAkland"
    private val uuid = "bb033844e68e4909a6361a5d1821ddc4"

    @Test
    fun testGetSkinHeadWithUsername() {
        val result = SkinHeadUtil.getSkinFaviconWithUsername(username)
        assertNotNull(result)
    }

    @Test
    fun testGetSkinHeadWithUUID() {
        val result = SkinHeadUtil.getSkinFaviconWithUUID(uuid)
        assertNotNull(result)
    }
}