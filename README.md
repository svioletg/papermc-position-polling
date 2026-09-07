# PositionPolling

> [!IMPORTANT]
> Breaking changes are likely between 0.x releases.

A [PaperMC](https://papermc.io/) plugin for Minecraft servers that periodically
logs every player's position to a local SQLite database, based on a
user-configured interval, to be used for things like creating heatmap
visualizations.

A companion Python package is also available to easily render visualizations
from your collected data: <https://github.com/svioletg/papermc-position-polling-tools>

## Usage

Records are saved in your `plugins` folder at `PositionPolling/data.db`.

The database has a single `player_positions` table with this structure:

| Column        | SQL Type | Description                                                              |
|---------------|----------|--------------------------------------------------------------------------|
| `timestamp`   | REAL     | Unix epoch timestamp at which this position was logged, in seconds.      |
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

- `/positionpolling status`: Shows whether position logging is currently enabled or not
- `/positionpolling on`: Enables position logging
- `/positionpolling off`: Disables position logging
- `/positionpolling count`: Shows how many position logs have been recorded
