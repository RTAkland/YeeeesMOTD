package cn.rtast.yeeesmotd;

import cn.rtast.yeeesmotd.command.YesMOTDCommand;
import cn.rtast.yeeesmotd.listeners.ProxyPingEventListener;
import cn.rtast.yeeesmotd.listeners.ServerConnectedEventListener;
import cn.rtast.yeeesmotd.listeners.ServerPreConnectEventListener;
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
        url = "https://github.com/RTAkland/YeeesMOTD",
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
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        proxy.getEventManager().register(this, new ProxyPingEventListener());
        proxy.getEventManager().register(this, new ServerConnectedEventListener(proxy));
        proxy.getEventManager().register(this, new ServerPreConnectEventListener());

        CommandManager commandManager = proxy.getCommandManager();
        CommandMeta commandMeta = commandManager.metaBuilder("yesmotd")
                .aliases("reload", "clear")
                .plugin(this)
                .build();
        var yesMOTDCommand = new YesMOTDCommand();
        commandManager.register(commandMeta, yesMOTDCommand);
    }
}
