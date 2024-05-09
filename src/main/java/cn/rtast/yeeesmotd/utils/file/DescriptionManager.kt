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


package cn.rtast.yeeesmotd.utils.file

import cn.rtast.yeeesmotd.DEFAULT_DESCRIPTION
import cn.rtast.yeeesmotd.YeeeesMOTDPlugin
import cn.rtast.yeeesmotd.entity.file.Description
import com.google.gson.reflect.TypeToken
import kotlin.random.Random

class DescriptionManager : IJsonManager<MutableList<Description>>("description.json", mutableListOf()) {

    init {
        if (this.file.readText() == "[]") {
            this.file.writeText(DEFAULT_DESCRIPTION)
        }
    }

    private val buildIn = listOf(
        "\$player <#A020F0>是吧? <#A020F0>还不赶快进来\n不然有你好果子吃的!",
        "<bold><#EE82EE>这是 \$player <bold><#EE82EE>的专属服务器呀~",
        "<bold><#CD96CD>逸一时误一世! \n\$player <bold><#CD96CD>赶快给我进来!"
    )

    private fun read(): MutableList<Description> {
        val str = this.file.readText()
        val type = object : TypeToken<MutableList<Description>>() {}.type
        return YeeeesMOTDPlugin.gson.fromJson(str, type)
    }

    private fun write(data: MutableList<Description>) {
        val serData = YeeeesMOTDPlugin.gson.toJson(data)
        this.file.writeText(serData)
    }

    fun randomBuildInDesc(): String {
        return buildIn[Random.nextInt(0, buildIn.size)]
    }

    fun randomDescription(): Description? {
        val allDesc = this.read()

        if (allDesc.isEmpty()) {
            return null
        }
        return allDesc[Random.nextInt(0, allDesc.size)]
    }
}