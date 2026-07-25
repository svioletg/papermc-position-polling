# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added

- Added command `/positionpolling status`
  - Shows whether position logging is currently enabled

### Changed

- Table `player_positions` column `epoch_ms` renamed to `timestamp`, type changed from `INTEGER`
  to `REAL`
  - Now stores time since Unix epoch in seconds instead of milliseconds

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
