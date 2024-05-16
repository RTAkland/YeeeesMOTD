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

import cn.rtast.yeeeesmotd.entity.PingRecord
import cn.rtast.yeeeesmotd.gson
import com.google.gson.reflect.TypeToken
import java.time.Instant

class PingRecordManager : IJsonManager<MutableList<PingRecord>>("pingRecord.json", mutableListOf()) {

    init {
        if (this.file.readText().isEmpty()) {
            this.file.writeText("[]")
        }
    }

    private fun read(): MutableList<PingRecord> {
        val str = this.file.readText()
        val type = object : TypeToken<MutableList<PingRecord>>() {}.type
        return gson.fromJson(str, type)
    }

    private fun write(data: MutableList<PingRecord>) {
        val serData = gson.toJson(data)
        this.file.writeText(serData)
    }

    fun addRecord(ip: String) {
        val allRecords = this.read()
        val timestamp = Instant.now().epochSecond
        allRecords.add(PingRecord(ip, timestamp))
        this.write(allRecords)
    }

    fun removeRecord(ip: String) {
        val allRecords = this.read()
        allRecords.removeIf { it.ip == ip }
        this.write(allRecords)
    }

    fun exists(ip: String): Boolean {
        val allRecords = this.read()
        return allRecords.find { it.ip == ip }?.let { true } ?: false
    }

    fun getRecord(ip: String): PingRecord {
        val allRecords = this.read()
        return allRecords.find { it.ip == ip } ?: PingRecord(ip, Instant.now().epochSecond)
    }
}