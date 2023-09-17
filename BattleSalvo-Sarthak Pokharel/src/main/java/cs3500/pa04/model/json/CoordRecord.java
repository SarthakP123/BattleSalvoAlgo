package cs3500.pa04.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the Record for Coordinate
 *
 * @param x the x coordinate
 * @param y the y coordinate
 */
public record CoordRecord(
    @JsonProperty("x") int x,
    @JsonProperty("y") int y) {
}
