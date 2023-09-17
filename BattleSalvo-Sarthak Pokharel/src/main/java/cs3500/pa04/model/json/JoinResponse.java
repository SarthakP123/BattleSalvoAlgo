package cs3500.pa04.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.model.GameType;

/**
 * Represents the record for joining the server
 *
 * @param name the name of the team
 * @param type the game type we want to play
 */
public record JoinResponse(
    @JsonProperty("name") String name,
    @JsonProperty("game-type") GameType type) {
}
