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
import java.util.*
import kotlin.concurrent.thread

class CSVHeadImageManager {

    private val file = File(ROOT_PATH, "csv_head.csv")

    init {
        val configPath = File(ROOT_PATH)
        if (!configPath.exists()) configPath.mkdirs()
        this.file.createNewFile()
    }

    private fun readCSV(): MutableList<MutableList<String>> {
        val lines = this.file.readLines()
        return lines.map { line ->
            line.split(",").toMutableList()
        }.toMutableList()
    }

    private fun writeCSV(data: MutableList<MutableList<String>>) {
        this.file.printWriter().use { out ->
            data.forEach { row ->
                out.println(row.joinToString(","))
            }
        }
    }

    fun addHead(name: String, uuid: String, ip: String) {
        thread {
            val allHeads = this.readCSV()
            val skin = SkinUtil.getSkinFavicon(uuid)
            val encodedSkinImage = String(Base64.getEncoder().encode(skin), Charsets.UTF_8)
            allHeads.add(mutableListOf(name, uuid, encodedSkinImage, ip))
            this.writeCSV(allHeads)
        }
    }

    fun exists(value: String): Boolean {
        val allHeads = this.readCSV()
        allHeads.forEach {
            if (it.contains(value)) {
                return true
            }
        }
        return false
    }

    fun updateHead(name: String, uuid: String, ip: String) {
        thread {
            val allHeads = this.readCSV()
            var found = false
            allHeads.forEachIndexed { index, head ->
                if (head.contains(uuid)) {
                    found = true
                    val skin = SkinUtil.getSkinFavicon(uuid)
                    val encodedSkinImage = String(Base64.getEncoder().encode(skin), Charsets.UTF_8)
                    allHeads[index] = mutableListOf(name, uuid, encodedSkinImage, ip)
                    return@forEachIndexed
                }
            }
            if (found) {
                this.writeCSV(allHeads)
            }
        }
    }

    fun getHead(ip: String): List<String> {
        val allHeads = this.readCSV()
        for (allHead in allHeads) {
            if (allHead.contains(ip)) {
                return allHead
            }
        }
        return listOf("", "", "", "")
    }
}
