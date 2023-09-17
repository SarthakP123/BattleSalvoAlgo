package cs3500.pa04.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.model.GameResult;

/**
 * Represents the record for the end game Message
 *
 * @param result The result of the match
 * @param reason Reason for the win
 */
public record EndMessage(
    @JsonProperty("result") GameResult result,
    @JsonProperty("reason") String reason) {
}
