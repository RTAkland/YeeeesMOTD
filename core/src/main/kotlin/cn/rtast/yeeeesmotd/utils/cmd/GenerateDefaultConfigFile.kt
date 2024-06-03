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


package cn.rtast.yeeeesmotd.utils.cmd

import cn.rtast.yeeeesmotd.DEFAULT_CONFIG
import cn.rtast.yeeeesmotd.gson
import java.io.File

fun main() {
    val defaultConfigFile = File("./default.config.json")
    println("New schema version: ${DEFAULT_CONFIG.schemaVersion}")
    val obj = gson.toJson(DEFAULT_CONFIG)
    defaultConfigFile.writeText(obj)
    println("Done")
}