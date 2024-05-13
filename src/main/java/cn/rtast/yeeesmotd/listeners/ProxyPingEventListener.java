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

import cn.rtast.yeeesmotd.ConstKt;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyPingEvent;
import com.velocitypowered.api.util.Favicon;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

import static cn.rtast.yeeesmotd.YeeeesMOTDPlugin.*;

public class ProxyPingEventListener {

    @Subscribe
    public void onProxyPingEvent(ProxyPingEvent event) throws IOException {
        var ip = event.getConnection().getRemoteAddress().getHostName();

        var favicon = new Favicon(ConstKt.DEFAULT_ICON);
        var showHead = new Random().nextBoolean();

        var randomDescription = configManager.getRandomDescription();
        var finalDescription = Component.text();
        if (randomDescription == null) {
            finalDescription.append(event.getPing().getDescriptionComponent());
        } else {
            finalDescription
                    .append(miniMessage.deserialize(randomDescription.getLine1()))
                    .append(Component.text("\n"))
                    .append(miniMessage.deserialize(randomDescription.getLine2()));
        }

        if (showHead && skinHeadManager.exists(ip)) {
            var userData = skinHeadManager.getHead(ip);
            var decodedHead = Base64.getDecoder().decode(userData.getHead());
            var bufferedHead = ImageIO.read(new ByteArrayInputStream(decodedHead));
            favicon = Favicon.create(bufferedHead);

            var randomBuildInDesc =
                    configManager.getRandomBuildInDescription().split("\\$player");
            finalDescription = Component.text()
                    .append(miniMessage.deserialize(randomBuildInDesc[0]))
                    .append(Component.text(userData.getName()).style(style -> style.color(TextColor.color(0xEE82EE))))
                    .append(miniMessage.deserialize(randomBuildInDesc[randomBuildInDesc.length - 1]));
        } else {
            var randomIcon = faviconManager.getRandomIcon();
            if (randomIcon != null) {
                favicon = Favicon.create(randomIcon);
            }
        }

        var pong = event.getPing().asBuilder();
        pong.description(finalDescription.build())
                .favicon(favicon)
                .onlinePlayers(configManager.getConfig().getOnlinePlayer())
                .maximumPlayers(configManager.getConfig().getMaximumPlayer());

        if (configManager.getConfig().getClearSamplePlayer()) {
            pong.clearSamplePlayers();
        }

        event.setPing(pong.build());

        if (configManager.pingPass().getEnabled()) {
            if (pingRecordManager.exists(ip)) {
                pingRecordManager.removeRecord(ip);
            }
            pingRecordManager.addRecord(ip);
        }
    }
}
