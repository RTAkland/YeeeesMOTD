package cn.rtast.yeeeesmotd.bukkit

import cn.rtast.yeeeesmotd.bukkit.command.YeeeesMOTDCommand
import cn.rtast.yeeeesmotd.bukkit.events.PlayerJoinEventListener
import cn.rtast.yeeeesmotd.bukkit.events.ServerListPingEventListener
import cn.rtast.yeeeesmotd.utils.file.*
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.plugin.java.JavaPlugin

class YeeeesMOTDPlugin : JavaPlugin() {

    companion object {
        val miniMessage: MiniMessage = MiniMessage.miniMessage()

        val faviconManager: FaviconManager = FaviconManager()
        val skinHeadManager: SkinHeadManager = SkinHeadManager()
        val pingRecordManager: PingRecordManager = PingRecordManager()
        val configManager: ConfigManager = ConfigManager()
        val hitokotoManager = HitokotoManager(configManager.hitokoto().type)
    }

    override fun onEnable() {
        server.pluginManager.registerEvents(ServerListPingEventListener(server), this)
        server.pluginManager.registerEvents(PlayerJoinEventListener(server), this)

        this.getCommand("yesmotd")?.setExecutor(YeeeesMOTDCommand())
    }
}
