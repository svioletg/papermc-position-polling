package io.github.svioletg.positionPolling

import net.kyori.adventure.text.Component
import org.bukkit.Location
import java.time.LocalTime
import java.util.UUID

class GetPositionTask(private val plugin: PositionPolling) : Runnable {
    override fun run() {
        if (!this.plugin.doPolling) return

        this.plugin.db.recordPlayerPos(this.plugin.server.onlinePlayers.toTypedArray())
    }
}
