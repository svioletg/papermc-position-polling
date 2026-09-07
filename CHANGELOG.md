# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [0.3.0]

### Added

- Added command `/positionpolling count`
- Added method `DatabaseManager.entryCount()`
- Added method `PluginCommands.entryCount()`
- Added property `PositionPolling.meta` for access to plugin metadata
- Added table `plugin_info` with `version` column consisting of a single row

## [0.2.0] - 2026-08-10

### Added

- Added a warning message on plugin startup if `poll-rate-ticks` is <= 20
- Added command `/positionpolling status`
  - Shows whether position logging is currently enabled
- Added property `PositionPolling.pollTask` (nullable)
  - The `BukkitTask` object for the position polling task, if it exists

### Changed

- Table `player_positions` column `epoch_ms` renamed to `timestamp`, type changed from `INTEGER`
  to `REAL`
  - Now stores time since Unix epoch in seconds instead of milliseconds
- Player coordinates are now explicitly converted to integers before storing

## [0.1.0] - 2026-07-16

Initial beta release.

### Added

- Added config key `poll-rate-ticks` (integer)
  - Interval of ticks to wait between each position log
- Added config key `start-polling-on-start` (bool)
  - Whether to immediately start logging positions on plugin startup. If false,
  `/positionpolling on` must be used to enable it once the server is started
- Added commands `/positionpolling on` and `/positionpolling off`
  - Enables and disables position logging respectively
- Added class `DatabaseManager`
- Added class `GetPositionTask`
- Added class `PluginCommands`
- Added class `PositionPolling`
