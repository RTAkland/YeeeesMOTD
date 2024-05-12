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


package cn.rtast.yeeesmotd.listeners;

import cn.rtast.yeeesmotd.YeeeesMOTDPlugin;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerPreConnectEvent;
import net.kyori.adventure.text.Component;

import java.time.Instant;

public class ServerPreConnectEventListener {

    public static String PING_FIRST_TEXT  = YeeeesMOTDPlugin.configManager.pingFirst().getPingFirstText();
    public static String RE_PING_TEXT = YeeeesMOTDPlugin.configManager.pingFirst().getRePingText();

    @Subscribe
    public void onServerPreConnect(ServerPreConnectEvent event) {
        if (YeeeesMOTDPlugin.configManager.pingFirst().getEnabled()) {
            var ip = event.getPlayer().getRemoteAddress().getHostName();
            if (YeeeesMOTDPlugin.pingRecordManager.exists(ip)) {
                var record = YeeeesMOTDPlugin.pingRecordManager.getRecord(ip);
                var currentTimestamp = Instant.now().getEpochSecond();
                var interval = YeeeesMOTDPlugin.configManager.pingFirst().getInterval();
                if (currentTimestamp - record.getTimestamp() > interval) {
                    event.getPlayer().disconnect(Component.text(RE_PING_TEXT));
                    YeeeesMOTDPlugin.pingRecordManager.removeRecord(ip);
                }
            } else {
                event.getPlayer().disconnect(Component.text(PING_FIRST_TEXT));
            }
        }
    }
}
