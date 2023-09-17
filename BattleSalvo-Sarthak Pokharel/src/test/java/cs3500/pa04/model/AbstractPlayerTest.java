package cs3500.pa04.model;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Represents the test for the abstract player
 */
public class AbstractPlayerTest {

  private Player player;
  private Map<ShipType, Integer> shipSpecifications;
  private Random rng;

  /**
   * To do before every tests
   */
  @BeforeEach
  public void setup() {
    rng = new Random(0);
    player = new AbstractPlayer(rng) {
      @Override
      public String name() {
        return "Test Player";
      }

      @Override
      public List<Coord> takeShots() {
        return null;
      }
    };
    shipSpecifications = new HashMap<>();
    shipSpecifications.put(ShipType.SUBMARINE, 2);
    shipSpecifications.put(ShipType.CARRIER, 1);
  }

  /**
   * Tests the setup method
   */
  @Test
  public void testSetup() {
    List<Ship> ships = player.setup(10, 10, shipSpecifications);

    assertEquals(3, ships.size());
    assertTrue(ships.stream().anyMatch(ship -> ship.getShipType() == ShipType.SUBMARINE));
    assertTrue(ships.stream().anyMatch(ship -> ship.getShipType() == ShipType.CARRIER));

    List<Coord> allShipCoords = ships.stream()
        .flatMap(ship -> ship.getShipCoordinates().stream())
        .toList();
    Set<Coord> uniqueCoords = new HashSet<>(allShipCoords);
    assertEquals(allShipCoords.size(), uniqueCoords.size());
  }

  /**
   * Tests for the report damage method
   */
  @Test
  public void testReportDamage() {
    List<Ship> ships = player.setup(10, 10, shipSpecifications);
    List<Coord> opponentShots = ships.get(0).getShipCoordinates();

    List<Coord> hits = player.reportDamage(opponentShots);

    assertEquals(opponentShots.size(), hits.size());
    assertTrue(opponentShots.containsAll(hits));
  }
}
