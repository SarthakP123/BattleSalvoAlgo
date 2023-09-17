package cs3500.pa04.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Represents the record for the network message
 *
 * @param method the name of the method
 * @param args   the arguments passed in
 */
public record NetMessage(
    @JsonProperty("method-name") String method,
    @JsonProperty("arguments") JsonNode args) {
}
