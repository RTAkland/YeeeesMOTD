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

import cn.rtast.yeeeesmotd.ROOT_PATH
import java.io.File
import java.net.URI
import java.util.*

class SamplePlayerGenerator {

    private val usernameDb = File(ROOT_PATH, "username.txt")
    private lateinit var allUsername: List<String>

    init {
        if (!this.usernameDb.exists()) {
            this.usernameDb.createNewFile()
            println("Downloading fake username list from: $USERNAME_DB")
            this.downloadUsernameDatabase()
            println("Fake username list downloaded")
            allUsername = this.usernameDb.readLines()
        }
    }

    companion object {
        private const val USERNAME_DB = "https://static.rtast.cn/static/username.txt"
    }

    private fun downloadUsernameDatabase() {
        this.usernameDb.writeText(URI(USERNAME_DB).toURL().readText())
    }

    private fun getRandomUsername(): String {
        return this.allUsername.random()
    }

    fun generate(count: Int): List<SamplePlayer> {
        val samples = mutableListOf<SamplePlayer>()
        (0..count).forEach { _ ->
            val name = this.getRandomUsername()
            val uuid = UUID.randomUUID().toString()
            samples.add(SamplePlayer(name, uuid))
        }

        return samples
    }

    data class SamplePlayer(
        val name: String,
        val uuid: String,
    )
}