package io.github.svioletg.positionPolling

import io.papermc.paper.command.brigadier.Commands
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import org.bukkit.plugin.java.JavaPlugin

class PositionPolling : JavaPlugin() {
    val db: DatabaseManager = DatabaseManager(this)
    var doPolling: Boolean = false

    override fun onEnable() {
        saveDefaultConfig()
        val config = getConfig()

        this.doPolling = config.getBoolean("start-polling-on-start")

        // Commands
        val pluginCommands = PluginCommands(this)

        val cmdRoot = Commands.literal("positionpolling")
        cmdRoot.then(pluginCommands.getPollingStatus())
        cmdRoot.then(pluginCommands.turnPollingOn())
        cmdRoot.then(pluginCommands.turnPollingOff())

        this.lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS, fun(commands) {
            commands.registrar().register(cmdRoot.build())
        })

        // Tasks
        val scheduler = this.server.scheduler
        scheduler.runTaskTimer(this, GetPositionTask(this), 0, config.getLong("poll-rate-ticks"))

        db.setup()
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
