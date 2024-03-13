package kr.mj.plugin.playerwarpitem

import kr.mj.plugin.playerwarpitem.command.AdminCommand
import kr.mj.plugin.playerwarpitem.listener.PlayerRightClickListener
import org.bukkit.plugin.java.JavaPlugin

class PlayerWarpItem : JavaPlugin() {
    override fun onEnable() {
        getCommand("워프아이템설정")?.setExecutor(AdminCommand())

        server.pluginManager.registerEvents(PlayerRightClickListener(), this)
    }
}