package io.github.svioletg.positionPolling

import com.mojang.brigadier.Command
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import net.kyori.adventure.text.Component

class PluginCommands(private val plugin: PositionPolling) {
    fun helloCommand(): LiteralArgumentBuilder<CommandSourceStack?>? {
        return Commands.literal("hello").executes(fun(ctx): Int {
            this.plugin.server.broadcast(Component.text("Hello!"))

            return Command.SINGLE_SUCCESS
        })
    }

    fun turnPollingOn(): LiteralArgumentBuilder<CommandSourceStack?>? {
        return Commands.literal("on").executes(fun(ctx): Int {
            this.plugin.doPolling = true
            this.plugin.server.broadcast(Component.text("polling: ${this.plugin.doPolling}"))

            return Command.SINGLE_SUCCESS
        })
    }

    fun turnPollingOff(): LiteralArgumentBuilder<CommandSourceStack?>? {
        return Commands.literal("off").executes(fun(ctx): Int {
            this.plugin.doPolling = false
            this.plugin.server.broadcast(Component.text("polling: ${this.plugin.doPolling}"))

            return Command.SINGLE_SUCCESS
        })
    }
}
