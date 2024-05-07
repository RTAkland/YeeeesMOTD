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

import cn.rtast.yeeeesmotd.ROOT_PATH
import java.io.File

open class ICSVManager(filename: String) {

    val file = File(ROOT_PATH, filename)

    init {
        val configPath = File(ROOT_PATH)
        if (!configPath.exists()) configPath.mkdirs()
        this.file.createNewFile()
    }

    fun readCSV(): MutableList<MutableList<String>> {
        val lines = this.file.readLines()
        return lines.map { line ->
            line.split(",").toMutableList()
        }.toMutableList()
    }

    fun writeCSV(data: MutableList<MutableList<String>>) {
        this.file.printWriter().use { out ->
            data.forEach { row ->
                out.println(row.joinToString(","))
            }
        }
    }

}