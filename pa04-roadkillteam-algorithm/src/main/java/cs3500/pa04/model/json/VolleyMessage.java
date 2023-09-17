package cs3500.pa04.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Represents the record for the list of coordiantes
 *
 * @param shots The list of shots records
 */
public record VolleyMessage(
    @JsonProperty("coordinates") List<CoordRecord> shots) {
}
