# Beacon Network CLI (Command Line Interface)
Java CLI client for accessing Beacon Network API.

## Requirements
### Beacon Network Client
Beacon Network CLI uses Beacon Network Client as its underlying client. Therefore the requirements are the same. Please,
follow the [Requirements](https://github.com/mcupak/beacon-network-client#requirements) section in the Beacon Network
Client readme.

## Example
### Beacon
To load information on a beacon, type in your console:
```
java -jar path/to/cli/cli.jar beacon --id amplab
```

Each command has help message. For the beacon command, it will look similar to the following:
```
java -jar path/to/cli/cli.jar beacon --help

Description: Loads information on beacon by id.
 -i (--id) VAL : Beacon Id
```