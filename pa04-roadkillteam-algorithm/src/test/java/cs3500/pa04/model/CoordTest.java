package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.*;

import cs3500.pa04.model.json.CoordRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * To test the coordinate class
 */
public class CoordTest {
  private Coord coord;
  private Ship ship;

  /**
   * Do before each tests
   */
  @BeforeEach
  public void setup() {
    coord = new Coord(5, 6);
    ship = new Ship(ShipType.DESTROYER);
  }

  /**
   * Tests the is hit method
   */
  @Test
  public void testIsHit() {
    assertFalse(coord.isHit());
    coord.doHit();
    assertTrue(coord.isHit());
  }

  /**
   * test the do hit method
   */
  @Test
  public void testDoHit() {
    assertFalse(coord.doHit()); // no ship on this coordinate, so first hit returns false
  }

  /**
   * Test the is occupied method
   */
  @Test
  public void testOccupied() {
    assertFalse(coord.occupied());
    coord.setShip(ship);
    assertTrue(coord.occupied());
  }

  /**
   * Tests the equals method
   */
  @Test
  public void testEquals() {
    Coord coord2 = new Coord(5, 6);
    Coord coord3 = new Coord(7, 8);
    assertEquals(coord, coord2);
    assertNotEquals(coord, coord3);
  }

  /**
   * Tests the Override method
   */
  @Test
  public void testToString() {
    assertEquals("-", coord.toString());
    coord.setShip(ship);
    assertEquals("D", coord.toString()); // assuming ship type is DESTROYER
    coord.doHit();
    assertEquals("X", coord.toString());
  }

  /**
   * Test the to record method
   */
  @Test
  public void testToRecord() {
    CoordRecord record = coord.toRecord();
    assertEquals(coord.x, record.x());
    assertEquals(coord.y, record.y());
  }
}
