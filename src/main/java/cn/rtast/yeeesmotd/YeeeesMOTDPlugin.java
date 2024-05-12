package cn.rtast.yeeesmotd

import cn.rtast.yeeesmotd.command.YesMOTDCommand
import cn.rtast.yeeesmotd.listeners.ProxyPingEventListener
import cn.rtast.yeeesmotd.listeners.ServerConnectedEventListener
import cn.rtast.yeeesmotd.utils.file.DescriptionManager
import cn.rtast.yeeesmotd.utils.file.FaviconManager
import cn.rtast.yeeesmotd.utils.file.SkinHeadManager
import com.google.gson.Gson
import com.google.inject.Inject
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.proxy.ProxyServer
import net.kyori.adventure.text.minimessage.MiniMessage
import org.slf4j.Logger

@Plugin(
    id = "yeeesmotd",
    name = "YeeesMOTD",
    version = BuildConstants.VERSION,
    description = "返回个性化的MOTD",
    url = "https://github.com/RTAkland/YeeesMOTD",
    authors = ["RTAkland"]
)
class YeeeesMOTDPlugin @Inject constructor(private val logger: Logger, private val proxy: ProxyServer) {
    init {
        faviconManager.setValidIcons()
    }

    @Subscribe
    fun onProxyInitialization(event: ProxyInitializeEvent?) {
        proxy.eventManager.register(this, ProxyPingEventListener())
        proxy.eventManager.register(this, ServerConnectedEventListener(proxy))
        val commandManager = proxy.commandManager

        val commandMeta = commandManager.metaBuilder("yesmotd")
            .aliases("reload", "clear")
            .plugin(this)
            .build()
        val yesMOTDCommand = YesMOTDCommand()
        commandManager.register(commandMeta, yesMOTDCommand)
    }

    companion object {
        var gson: Gson = Gson()
        var faviconManager: FaviconManager = FaviconManager()
        var descriptionManager: DescriptionManager = DescriptionManager()
        @JvmField
        var skinHeadManager: SkinHeadManager = SkinHeadManager()
        var miniMessage: MiniMessage = MiniMessage.miniMessage()
    }
}
