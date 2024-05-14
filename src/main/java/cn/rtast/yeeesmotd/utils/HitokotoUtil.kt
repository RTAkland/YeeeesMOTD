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
import com.google.gson.reflect.TypeToken
import java.io.File
import java.net.URI

class HitokotoUtil {

    private var sentenceFile: File

    private val hitokotoConfig = YeeeesMOTDPlugin.configManager.hitokoto()

    init {
        val hitokotoDir = File(ROOT_PATH, "hitokoto")
        hitokotoDir.mkdirs()

        val sentenceType = YeeeesMOTDPlugin.configManager.hitokoto().type
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
            YeeeesMOTDPlugin.gson.fromJson(this.sentenceFile.readText(), object : TypeToken<List<Config.Sentence>>() {})
        var randomSentence = sentences.random()
        while (true) {
            if (randomSentence.hitokoto.length > 25) {
                randomSentence = sentences.random()
                continue
            } else {
                break
            }
        }
        val spaces = " ".repeat(40)
        val description = Config.Description(
            "<${hitokotoConfig.color}>${randomSentence.hitokoto}",
            "<${hitokotoConfig.color}>$spaces--《${randomSentence.from}》"
        )
        return description
    }

    enum class SentenceType(val type: String, val index: Int) {
        ANIMATION("a", 0),
        COMICS("b", 1),
        GAMES("c", 2),
        LITERATURE("d", 3),
        ORIGINAL("e", 4),
        FROM_NETWORK("f", 5),
        OTHER("g", 6),
        FILM_AND_TELEVISION("h", 7),
        POETRY("i", 8),
        NETEASE_CLOUD_MUSIC("j", 9),
        PHILOSOPHY("k", 10),
        HUMOR("l", 11);
    }
}
