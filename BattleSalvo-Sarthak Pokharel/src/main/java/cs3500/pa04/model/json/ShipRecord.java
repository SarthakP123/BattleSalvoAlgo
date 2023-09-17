package cs3500.pa04.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a ship record
 *
 * @param start     Starting Coordinate
 * @param length    The length of a ship
 * @param direction The direction the ship is facing
 */
public record ShipRecord(
    @JsonProperty("coord") CoordRecord start,
    @JsonProperty("length") int length,
    @JsonProperty("direction") String direction) {
}
