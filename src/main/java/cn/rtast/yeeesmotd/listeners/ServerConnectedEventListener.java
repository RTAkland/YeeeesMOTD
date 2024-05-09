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

import cn.rtast.yeeesmotd.YeeesMOTDPlugin;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.util.GameProfile;
import kotlin.text.Charsets;

import java.util.Base64;

public class ServerConnectedEventListener {

    private final ProxyServer proxy;

    public ServerConnectedEventListener(ProxyServer server) {
        this.proxy = server;
    }

    private String getTextureContent(GameProfile profile) {
        var texturesBase64 = profile.getProperties().toString().split(",")[1].split("=")[1].replace("'", "");
        return new String(Base64.getDecoder().decode(texturesBase64), Charsets.UTF_8);
    }


    @Subscribe
    public void onServerConnected(ServerConnectedEvent event) {
        var ip = event.getPlayer().getRemoteAddress().getHostName();
        var name = event.getPlayer().getUsername();
        var uuid = event.getPlayer().getUniqueId().toString();
        this.getTextureContent(event.getPlayer().getGameProfile());

        if (proxy.getConfiguration().isOnlineMode()) {
            var textureContent = this.getTextureContent(event.getPlayer().getGameProfile());
            if (!YeeesMOTDPlugin.skinHeadManager.exists(ip)) {
                YeeesMOTDPlugin.skinHeadManager.addHead(name, uuid, ip, textureContent);
            } else {
                YeeesMOTDPlugin.skinHeadManager.updateHead(name, uuid, ip, textureContent);
            }
        }
    }
}
