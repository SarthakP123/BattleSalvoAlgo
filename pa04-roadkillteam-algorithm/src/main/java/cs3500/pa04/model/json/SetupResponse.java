package cs3500.pa04.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Represents the record the fleet which is a list of shipRecords
 *
 * @param fleet Represents a group of ship records
 */
public record SetupResponse(@JsonProperty("fleet") List<ShipRecord> fleet) {
}
