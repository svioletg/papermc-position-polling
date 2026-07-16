package io.github.svioletg.positionPolling

class GetPositionTask(private val plugin: PositionPolling) : Runnable {
    override fun run() {
        if (!this.plugin.doPolling) return

        this.plugin.db.recordPlayerPos(this.plugin.server.onlinePlayers.toTypedArray())
    }
}
