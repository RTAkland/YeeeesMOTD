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
import cn.rtast.yeeeesmotd.YeeeesMOTD
import cn.rtast.yeeeesmotd.entity.SkinsEntity
import java.io.File

class SkinHeadManager {

    private val file = File(ROOT_PATH, "heads.json")

    init {
        val configDir = File(ROOT_PATH)
        if (!configDir.exists()) {
            configDir.mkdirs()
        }

        if (!file.exists()) {
            file.createNewFile()
            val default = SkinsEntity(mutableListOf(SkinsEntity.User("0", "0", "0", "0")))
            file.writeText(YeeeesMOTD.gson.toJson(default))
        }
    }

    private fun getAllHeads(): SkinsEntity {
        return YeeeesMOTD.gson.fromJson(this.file.readText(), SkinsEntity::class.java)
    }

    private fun write(content: SkinsEntity) {
        val str = YeeeesMOTD.gson.toJson(content)
        this.file.writeText(str)
    }


    fun exists(ip: String): Boolean {
        val allHeads = this.getAllHeads().users
        return allHeads.find { it.ip == ip }?.let { true } ?: false
    }

    fun getHead(ip: String): SkinsEntity.User {
        val allHeads = this.getAllHeads().users
        return allHeads.find { it.ip == ip }!!
    }


    fun addHead(user: SkinsEntity.User) {
        var allHeads = this.getAllHeads()
        if (allHeads.users.isEmpty()) {
            allHeads = SkinsEntity(mutableListOf(user))
        } else {
            allHeads.users.add(user)
        }
        this.write(allHeads)
    }
}
