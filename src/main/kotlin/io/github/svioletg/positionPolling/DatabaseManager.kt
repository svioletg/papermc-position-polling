package io.github.svioletg.positionPolling

import org.bukkit.entity.Player
import java.sql.Connection
import java.sql.DriverManager

class DatabaseManager {
    private var conn: Connection? = null

    fun setup() {
        val conn = this.getConnection()
        val query = buildString {
            append("CREATE TABLE IF NOT EXISTS player_positions(")
            append("epoch_ms INTEGER, ")
            append("player_uuid TEXT, ")
            append("world TEXT, ")
            append("x INTEGER, ")
            append("y INTEGER, ")
            append("z INTEGER")
            append(");")
        }

        conn.createStatement().execute(query)
    }

    fun getConnection(): Connection {
        var conn = this.conn

        if (conn != null) {
            return conn
        }

        // Initialize driver
        Class.forName("org.sqlite.JDBC")
        conn = DriverManager.getConnection("jdbc:sqlite:plugins/position-polling/data.db")
        this.conn = conn

        return conn
    }

    fun recordPlayerPos(players: Array<Player>) {
        val conn = this.getConnection()
        val epoch = System.currentTimeMillis()

        val query = "INSERT INTO player_positions(epoch_ms, player_uuid, world, x, y, z) VALUES(?, ?, ?, ?, ?, ?);"
        val stmt = conn.prepareStatement(query)

        for (player in players) {
            val pos = player.location

            stmt.setLong(1, epoch)
            stmt.setString(2, player.uniqueId.toString())
            stmt.setString(3, pos.world.key.toString())
            stmt.setDouble(4, pos.x)
            stmt.setDouble(5, pos.y)
            stmt.setDouble(6, pos.z)

            stmt.executeUpdate()
        }
    }
}
