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


package cn.rtast.yeeesmotd;

import cn.rtast.yeeesmotd.command.YesMOTDCommand;
import cn.rtast.yeeesmotd.listeners.*;
import cn.rtast.yeeesmotd.utils.file.ConfigManager;
import cn.rtast.yeeesmotd.utils.file.FaviconManager;
import cn.rtast.yeeesmotd.utils.file.PingRecordManager;
import cn.rtast.yeeesmotd.utils.file.SkinHeadManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.slf4j.Logger;

@Plugin(
        id = "yeeesmotd",
        name = "YeeesMOTD",
        version = BuildConstants.VERSION,
        description = "返回个性化的MOTD",
        url = "https://github.com/RTAkland/YeeeesMOTD",
        authors = {"RTAkland"}
)
public class YeeeesMOTDPlugin {

    private final Logger logger;
    private final ProxyServer proxy;

    public static Gson gson = new GsonBuilder().disableHtmlEscaping().create();
    public static MiniMessage miniMessage = MiniMessage.miniMessage();

    public static FaviconManager faviconManager = new FaviconManager();
    public static SkinHeadManager skinHeadManager = new SkinHeadManager();
    public static PingRecordManager pingRecordManager = new PingRecordManager();
    public static ConfigManager configManager = new ConfigManager();

    @Inject
    public YeeeesMOTDPlugin(Logger logger, ProxyServer proxy) {
        this.logger = logger;
        this.proxy = proxy;
        faviconManager.setValidIcons();
        this.logger.debug("YeeeesMOTD初始化完成");
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        proxy.getEventManager().register(this, new ProxyPingEventListener());
        proxy.getEventManager().register(this, new ServerConnectedEventListener(proxy));
        proxy.getEventManager().register(this, new LoginEventListener());


        CommandManager commandManager = proxy.getCommandManager();
        CommandMeta commandMeta = commandManager.metaBuilder("yesmotd")
                .aliases("reload", "clear")
                .plugin(this)
                .build();
        var yesMOTDCommand = new YesMOTDCommand();
        commandManager.register(commandMeta, yesMOTDCommand);
    }
}
