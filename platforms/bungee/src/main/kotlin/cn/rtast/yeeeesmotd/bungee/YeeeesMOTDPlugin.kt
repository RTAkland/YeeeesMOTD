package cn.rtast.yeeeesmotd.bungee

import cn.rtast.yeeeesmotd.IYeeeesMOTD
import cn.rtast.yeeeesmotd.IYeeeesMOTD.Companion.faviconManager
import cn.rtast.yeeeesmotd.bungee.command.YeeeesMOTDCommand
import cn.rtast.yeeeesmotd.bungee.listeners.PostLoginEventListener
import cn.rtast.yeeeesmotd.bungee.listeners.ProxyPingEventListener
import net.kyori.adventure.platform.bungeecord.BungeeAudiences
import net.kyori.adventure.text.minimessage.MiniMessage
import net.md_5.bungee.api.plugin.Plugin

class YeeeesMOTDPlugin : Plugin(), IYeeeesMOTD {

    companion object {
        lateinit var adventure: BungeeAudiences
        val miniMessage = MiniMessage.miniMessage()
    }

    init {
        faviconManager.setValidIcons()
        adventure = BungeeAudiences.create(this)
    }

    override fun onEnable() {
        this.proxy.pluginManager.registerListener(this, ProxyPingEventListener())
        this.proxy.pluginManager.registerListener(this, PostLoginEventListener(this.proxy))
        this.proxy.pluginManager.registerCommand(this, YeeeesMOTDCommand("yeeeesmotd"))
        logger.info("YeeeesMOTD is enabled")
    }

    override fun onDisable() {
        adventure.close()
        logger.info("YeeeesMOTD is disabled")
    }
}
