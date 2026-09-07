package io.github.svioletg.positionPolling

import io.papermc.paper.command.brigadier.Commands
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitTask

class PositionPolling : JavaPlugin() {
    val db: DatabaseManager = DatabaseManager(this)
    var doPolling: Boolean = false
    var pollTask: BukkitTask? = null

    override fun onEnable() {
        saveDefaultConfig()
        val config = getConfig()

        val pollRate = config.getLong("poll-rate-ticks")

        if (pollRate <= 20) {
            this.logger.warning("poll-rate-ticks is set to a value lower than 20,"
                + " logging positions every second or less is not recommended")
        }

        this.doPolling = config.getBoolean("start-polling-on-start")

        // Commands
        val pluginCommands = PluginCommands(this)

        val cmdRoot = Commands.literal("positionpolling")
        cmdRoot.then(pluginCommands.getPollingStatus())
        cmdRoot.then(pluginCommands.entryCount())
        cmdRoot.then(pluginCommands.turnPollingOn())
        cmdRoot.then(pluginCommands.turnPollingOff())

        this.lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS, fun(commands) {
            commands.registrar().register(cmdRoot.build())
        })

        // Tasks
        val scheduler = this.server.scheduler
        this.pollTask = scheduler.runTaskTimer(this, GetPositionTask(this), 0, pollRate)

        db.setup()
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
