package io.github.svioletg.positionPolling

import org.bukkit.entity.Player
import java.sql.Connection
import java.sql.DriverManager

class DatabaseManager(private val plugin: PositionPolling) {
    private var conn: Connection? = null

    fun setup() {
        val conn = this.getConnection()

        conn.createStatement().execute(buildString {
            append("CREATE TABLE IF NOT EXISTS plugin_info(")
            append("version TEXT")
            append(");")
        })

        // Ensure there's a single row, delete any exists ones and rebuild
        conn.createStatement().execute("DELETE FROM plugin_info;")
        conn.createStatement().execute(buildString {
            append("INSERT INTO plugin_info(")
            append("version")
            append(") VALUES (")
            append("'${this@DatabaseManager.plugin.meta.version}'")
            append(");")
        })

        conn.createStatement().execute(buildString {
            append("CREATE TABLE IF NOT EXISTS player_positions(")
            append("timestamp REAL, ")
            append("player_uuid TEXT, ")
            append("world TEXT, ")
            append("x INTEGER, ")
            append("y INTEGER, ")
            append("z INTEGER")
            append(");")
        })
    }

    fun getConnection(): Connection {
        var conn = this.conn

        if (conn != null) {
            return conn
        }

        // Initialize driver
        Class.forName("org.sqlite.JDBC")
        conn = DriverManager.getConnection("jdbc:sqlite:plugins/${this.plugin.name}/data.db")
        this.conn = conn

        return conn
    }

    fun recordPlayerPos(players: Array<Player>) {
        val conn = this.getConnection()
        val epoch = System.currentTimeMillis() / 1000

        val query = "INSERT INTO player_positions(timestamp, player_uuid, world, x, y, z) VALUES(?, ?, ?, ?, ?, ?);"

        for (player in players) {
            val pos = player.location

            val stmt = conn.prepareStatement(query)

            stmt.setLong(1, epoch)
            stmt.setString(2, player.uniqueId.toString())
            stmt.setString(3, pos.world.key.toString())
            stmt.setInt(4, pos.x.toInt())
            stmt.setInt(5, pos.y.toInt())
            stmt.setInt(6, pos.z.toInt())

            stmt.executeUpdate()
        }
    }

    fun entryCount(): Int {
        val conn = this.getConnection()
        var count = 0
        val query = "SELECT * FROM player_positions;"
        val result = conn.createStatement().executeQuery(query)
        while (result.next()) {
            count += 1
        }

        return count
    }
}
