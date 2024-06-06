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

import cn.rtast.yeeeesmotd.ROOT_PATH
import cn.rtast.yeeeesmotd.utils.SamplePlayerGenerator
import java.io.File
import kotlin.test.Test
import kotlin.test.assertNotNull

class SamplePlayerGeneratorTest {

    private var generator: SamplePlayerGenerator

    init {
        File(ROOT_PATH).mkdirs()
        generator = SamplePlayerGenerator()
    }

    @Test
    fun testSamplePlayerGenerator() {
        val players = this.generator.generate(100)
        assertNotNull(players)
    }
}