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

import cn.rtast.yeeesmotd.HITOKOTO_SENTENCE_URL
import cn.rtast.yeeesmotd.ROOT_PATH
import cn.rtast.yeeesmotd.YeeeesMOTDPlugin
import cn.rtast.yeeesmotd.entity.Config
import cn.rtast.yeeesmotd.entity.Sentence
import com.google.gson.reflect.TypeToken
import java.io.File
import java.net.URI

class HitokotoUtil {

    private var sentenceFile: File

    private val hitokotoConfig = YeeeesMOTDPlugin.configManager.hitokoto()

    init {
        val hitokotoDir = File(ROOT_PATH, "hitokoto")
        hitokotoDir.mkdirs()

        val type = YeeeesMOTDPlugin.configManager.hitokoto().type
        val sentenceType = SentenceType.getType(type)?.type ?: "all"
        val file = File(ROOT_PATH, "hitokoto/$sentenceType.json")
        if (!file.exists()) {
            this.downloadSentence(sentenceType, file)
        }

        sentenceFile = file
    }

    private fun downloadSentence(type: String, file: File) {
        println("正在下载Hitokoto语句: $type.json")
        val content = URI("$HITOKOTO_SENTENCE_URL/$type.json").toURL().readText()
        file.writeText(content)
        println("Hitokoto语句下载完成")
    }

    fun getSentence(): Config.Description {
        val sentences =
            YeeeesMOTDPlugin.gson.fromJson(this.sentenceFile.readText(), object : TypeToken<List<Sentence>>() {})
        val randomSentence = sentences.random()
        val spaces = " ".repeat(40)
        val description = Config.Description(
            "<${hitokotoConfig.color}>${randomSentence.hitokoto}",
            "<${hitokotoConfig.color}>$spaces--《${randomSentence.from}》"
        )
        return description
    }

    enum class SentenceType(val type: String) {
        ALL("all"),
        ANIMATION("a"),
        COMICS("b"),
        GAMES("c"),
        LITERATURE("d"),
        ORIGINAL("e"),
        FROM_NETWORK("f"),
        OTHER("g"),
        FILM_AND_TELEVISION("h"),
        POETRY("i"),
        NETEASE_CLOUD_MUSIC("j"),
        PHILOSOPHY("k"),
        HUMOR("l");

        companion object {
            fun getType(value: String): SentenceType? {
                for (i in entries) {
                    if (i.type == value) {
                        return i
                    }
                }
                return null
            }
        }
    }
}
