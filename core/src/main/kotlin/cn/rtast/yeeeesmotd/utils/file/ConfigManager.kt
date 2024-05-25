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

import cn.rtast.yeeeesmotd.BUILD_IN_DESCRIPTIONS
import cn.rtast.yeeeesmotd.DEFAULT_CONFIG
import cn.rtast.yeeeesmotd.DEFAULT_CONFIG_URL
import cn.rtast.yeeeesmotd.entity.Config
import cn.rtast.yeeeesmotd.gson
import com.google.gson.JsonObject

class ConfigManager :
    IJsonManager<Config>(
        "config.json",
        DEFAULT_CONFIG
    ) {

    init {
        if (this.checkConfigConflict(DEFAULT_CONFIG)) {
            println(
                "Please go to $DEFAULT_CONFIG_URL to download the latest config file!!!!" +
                        "or you want to crash!!!!!"
            )
        }
    }

    private fun read(): Config {
        val str = this.file.readText()
        return gson.fromJson(str, Config::class.java)
    }

    private fun write(config: Config) {
        val serData = gson.toJson(config)
        this.file.writeText(serData)
    }

    private fun write(config: JsonObject) {
        val serData = gson.toJson(config)
        this.file.writeText(serData)
    }

    private fun checkConfigConflict(newConfig: Config): Boolean {
        return this.read().schemaVersion != newConfig.schemaVersion
    }

    fun getConfig(): Config {
        return this.read()
    }

    fun getRandomDescription(): Config.Description? {
        val config = this.read()

        val descriptions = config.descriptions
        if (descriptions.isEmpty()) {
            return null
        }
        return descriptions.random()
    }

    fun getRandomBuildInDescription(): String {
        return BUILD_IN_DESCRIPTIONS.random()
    }

    fun getRandomProtocolNumber(): Int? {
        val fakeProtocol = this.fakeProtocol()
        if (fakeProtocol.alwaysInvalidProtocolNumber) {
            return -1
        }
        if (fakeProtocol.protocolNumberPool.isEmpty()) {
            return null
        }
        return fakeProtocol.protocolNumberPool.random()
    }

    fun getRandomProtocolName(): String? {
        val protocolNamePool = this.fakeProtocol().protocolNamePool
        if (protocolNamePool.isEmpty()) {
            return null
        }
        return protocolNamePool.random()
    }

    fun pingPass(): Config.PingPass {
        return this.read().pingPass
    }

    fun hitokoto(): Config.Hitokoto {
        return this.read().hitokoto
    }

    fun fakeProtocol(): Config.FakeProtocol {
        return this.read().fakeProtocol
    }
}