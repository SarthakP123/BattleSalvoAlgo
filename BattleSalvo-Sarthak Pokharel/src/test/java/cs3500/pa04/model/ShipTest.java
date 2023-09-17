package cs3500.pa04.model;

import cs3500.pa04.model.json.ShipRecord;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Represents the tests for the ship class
 */
public class ShipTest {
  private Ship ship;
  private List<Coord> shipCoordinates;

  /**
   * Do this before each test
   */
  @BeforeEach
  public void setup() {
    ship = new Ship(ShipType.BATTLESHIP);
    shipCoordinates = new ArrayList<>();
    shipCoordinates.add(new Coord(0, 0));
    shipCoordinates.add(new Coord(0, 1));
    shipCoordinates.add(new Coord(0, 2));
    shipCoordinates.add(new Coord(0, 3));
    shipCoordinates.add(new Coord(0, 4));
    ship.setShipCoordinates(shipCoordinates);
  }

  /**
   * Check if the getter for ship type works
   */
  @Test
  public void testGetShipType() {
    assertEquals(ShipType.BATTLESHIP, ship.getShipType());
  }

  /**
   * Check if the getter for ship coordinate works
   */
  @Test
  public void testGetShipCoordinates() {
    assertEquals(shipCoordinates, ship.getShipCoordinates());
  }

  /**
   * Check if the is sunk method works
   */
  @Test
  public void testIsSunk() {
    assertFalse(ship.isSunk());
    for (Coord coord : shipCoordinates) {
      coord.doHit();
    }
    ship.updateSunk();
    assertTrue(ship.isSunk());
  }

  /**
   * Check if the record conversion works
   */
  @Test
  public void testToRecord() {
    ShipRecord record = ship.toRecord();
    assertEquals(ship.getShipType().getSize(), record.length());
    assertEquals(shipCoordinates.get(0).x, record.start().x());
    assertEquals(shipCoordinates.get(0).y, record.start().y());

    ShipDirection direction = ShipDirection.valueOf(record.direction());
    assertTrue(direction == ShipDirection.HORIZONTAL || direction == ShipDirection.VERTICAL);
  }
}
