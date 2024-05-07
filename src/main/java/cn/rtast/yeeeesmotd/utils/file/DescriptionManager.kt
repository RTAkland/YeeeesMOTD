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


package cn.rtast.yeeeesmotd.utils.file

import kotlin.random.Random

class DescriptionManager : ICSVManager("description.csv") {

    private val buildInDesc = listOf(
        "\$player 是吧? 还不赶紧进来\n不然有你好果汁吃!",
        "这是 \$player 的专属服务器呢~",
        "逸一时误一世! \n\$player 赶紧给我进来!"
    )


    fun randomBuildInDesc(): String {
        val rnd = Random.nextInt(0, buildInDesc.size)
        return buildInDesc[rnd]
    }

    fun randomDescription(): List<String>? {
        val allDesc = this.readCSV()
        if (allDesc.isEmpty()) {
            return null
        }

        return allDesc[Random.nextInt(0, allDesc.size)]
    }

}