# PositionPolling

A [PaperMC](https://papermc.io/) plugin for Minecraft servers that periodically
logs every player's position to a local SQLite database, based on a
user-configured interval, to be used for things like creating heatmap
visualizations.

The database has a single `player_positions` table with this structure:

| Column        | SQL Type | Description                                                              |
|---------------|----------|--------------------------------------------------------------------------|
| `epoch_ms`    | INTEGER  | Unix epoch timestamp at which this position was logged, in milliseconds. |
| `player_uuid` | TEXT     | The UUID of the player whose position was logged.                        |
| `world`       | TEXT     | The name of the world the player is in.                                  |
| `x`           | INTEGER  | Player's X coordinate.                                                   |
| `y`           | INTEGER  | Player's Y coordinate.                                                   |
| `z`           | INTEGER  | Player's Z coordinate.                                                   |

## Configuration

- `poll-rate-ticks`: How many ticks to wait between each check for positions
- `start-polling-on-startup`: Whether to immediately start logging positions once the plugin is
  enabled (use `/positionpolling on` to manually enable it)

## Commands

- `/positionpolling on`: Enables position logging
- `/positionpolling off`: Disables position logging
