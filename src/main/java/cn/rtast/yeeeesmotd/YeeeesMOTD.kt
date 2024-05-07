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


package cn.rtast.yeeeesmotd

import cn.rtast.yeeeesmotd.utils.CSVHeadImageManager
import cn.rtast.yeeeesmotd.utils.FaviconManager
import com.google.gson.Gson
import net.fabricmc.api.ModInitializer

class YeeeesMOTD : ModInitializer {

    companion object {
        val gson = Gson()

        val skinManager = CSVHeadImageManager()
        val iconManager = FaviconManager()
    }

    override fun onInitialize() {
        iconManager.setValidIcons()
    }
}