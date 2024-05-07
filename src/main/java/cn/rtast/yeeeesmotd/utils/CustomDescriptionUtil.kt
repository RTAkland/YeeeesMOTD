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

import kotlin.random.Random

object CustomDescriptionUtil {

    private val descList = listOf(
        "\$player 是吧, 赶紧进来\n不然有你好果汁吃!",
        "这是 \$player 的专属服务器捏~",
    )

    fun getDesc(): String {
        val rnd = Random.nextInt(0, descList.size)
        return this.descList[rnd]
    }
}
