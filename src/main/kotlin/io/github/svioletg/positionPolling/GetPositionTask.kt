package io.github.svioletg.positionPolling

import net.kyori.adventure.text.Component
import org.bukkit.Location
import java.time.LocalTime
import java.util.UUID

class GetPositionTask(private val plugin: PositionPolling) : Runnable {
    override fun run() {
        if (!this.plugin.doPolling) return

        val time = System.currentTimeMillis()
        this.plugin.db.recordPlayerPos(this.plugin.server.onlinePlayers.toTypedArray())
        for (player in this.plugin.server.onlinePlayers) {
            val name: String = player.name
            val uuid: UUID = player.uniqueId
            val pos: Location = player.location
            this.plugin.server.broadcast(Component.text("(${time}) $name ($uuid) at: ${pos.x} ${pos.y} ${pos.z}"))
        }
    }
}
