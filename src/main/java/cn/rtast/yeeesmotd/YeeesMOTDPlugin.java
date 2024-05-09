package cn.rtast.yeeesmotd;

import cn.rtast.yeeesmotd.command.YesMOTDCommand;
import cn.rtast.yeeesmotd.listeners.ProxyPingEventListener;
import cn.rtast.yeeesmotd.listeners.ServerConnectedEventListener;
import cn.rtast.yeeesmotd.utils.file.DescriptionManager;
import cn.rtast.yeeesmotd.utils.file.FaviconManager;
import cn.rtast.yeeesmotd.utils.file.SkinHeadManager;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

@Plugin(
        id = "yeeesmotd",
        name = "YeeesMOTD",
        version = BuildConstants.VERSION,
        description = "返回个性化的MOTD",
        url = "https://github.com/RTAkland/YeeesMOTD",
        authors = {"RTAkland"}
)
public class YeeesMOTDPlugin {

    private final Logger logger;
    private final ProxyServer proxy;

    public static Gson gson = new Gson();
    public static FaviconManager faviconManager = new FaviconManager();
    public static DescriptionManager descriptionManager = new DescriptionManager();
    public static SkinHeadManager skinHeadManager = new SkinHeadManager();

    @Inject
    public YeeesMOTDPlugin(Logger logger, ProxyServer proxy) {
        this.logger = logger;
        this.proxy = proxy;
        faviconManager.setValidIcons();
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        proxy.getEventManager().register(this, new ProxyPingEventListener());
        proxy.getEventManager().register(this, new ServerConnectedEventListener(proxy));
        CommandManager commandManager = proxy.getCommandManager();

        CommandMeta commandMeta = commandManager.metaBuilder("yesmotd")
                .aliases("reload", "clear")
                .plugin(this)
                .build();
        BrigadierCommand commandToRegister = YesMOTDCommand.INSTANCE.createCommand(proxy);
        commandManager.register(commandMeta, commandToRegister);

    }
}
