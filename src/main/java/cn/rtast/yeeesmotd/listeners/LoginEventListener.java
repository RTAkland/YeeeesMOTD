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

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.LoginEvent;
import net.kyori.adventure.text.Component;

import java.time.Instant;

import static cn.rtast.yeeesmotd.YeeeesMOTDPlugin.configManager;
import static cn.rtast.yeeesmotd.YeeeesMOTDPlugin.pingRecordManager;

public class LoginEventListener {

    public static String PING_FIRST_TEXT = configManager.pingPass().getPingFirstText();
    public static String PING_AGAIN_TEXT = configManager.pingPass().getPingAgainText();

    @Subscribe
    public void onLogin(LoginEvent event) {
        if (configManager.pingPass().getEnabled()) {
            var ip = event.getPlayer().getRemoteAddress().getHostName();
            if (pingRecordManager.exists(ip)) {
                var record = pingRecordManager.getRecord(ip);
                var currentTimestamp = Instant.now().getEpochSecond();
                var interval = configManager.pingPass().getInterval();
                if (currentTimestamp - record.getTimestamp() > interval) {
                    event.getPlayer().disconnect(Component.text(PING_AGAIN_TEXT));
                    pingRecordManager.removeRecord(ip);
                }
            } else {
                event.getPlayer().disconnect(Component.text(PING_FIRST_TEXT));
            }
        }
    }
}
