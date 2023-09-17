package cs3500.pa04.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.model.ShipType;
import java.util.Map;

/**
 * Represents the setUp record of the game
 *
 * @param width  The width of the board
 * @param height The height of the board
 * @param spec   The amount of each ship we want.
 */
public record SetupMessage(
    @JsonProperty("width") int width,
    @JsonProperty("height") int height,
    @JsonProperty("fleet-spec") Map<ShipType, Integer> spec) {
}
