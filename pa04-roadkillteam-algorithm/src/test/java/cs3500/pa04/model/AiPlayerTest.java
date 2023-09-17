package cs3500.pa04.model;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Represents the tests for the AI player class
 */
class AiPlayerTest {
  /**
   * Tests the name method
   */
  @Test
  void testAiPlayerName() {
    AiPlayer ai = new AiPlayer();
    assertEquals("AI", ai.name());
  }

  /**
   * Tests the setup method
   */
  @Test
  void testAiPlayerSetup() {
    AiPlayer ai = new AiPlayer();
    Map<ShipType, Integer> ships = new HashMap<>();
    ships.put(ShipType.CARRIER, 1);
    ships.put(ShipType.BATTLESHIP, 1);
    ships.put(ShipType.DESTROYER, 1);
    ships.put(ShipType.SUBMARINE, 1);

    List<Ship> shipList = ai.setup(10, 10, ships);

    assertNotNull(shipList);
    assertEquals(4, shipList.size());
  }

  /**
   * Tests the take shots method
   */
  @Test
  void testAiPlayerTakeShots() {
    AiPlayer ai = new AiPlayer();
    Map<ShipType, Integer> ships = new HashMap<>();
    ships.put(ShipType.CARRIER, 1);
    ships.put(ShipType.BATTLESHIP, 1);
    ships.put(ShipType.DESTROYER, 1);
    ships.put(ShipType.SUBMARINE, 1);
    ai.setup(10, 10, ships);

    List<Coord> shots = ai.takeShots();

    assertNotNull(shots);
    assertEquals(4, shots.size());
  }

  /**
   * Tests the SuccessfulHit method
   */
  @Test
  void testAiPlayerSuccessfulHits() {
    AiPlayer ai = new AiPlayer();
    Map<ShipType, Integer> ships = new HashMap<>();
    ships.put(ShipType.CARRIER, 1);
    ships.put(ShipType.BATTLESHIP, 1);
    ships.put(ShipType.DESTROYER, 1);
    ships.put(ShipType.SUBMARINE, 1);
    ai.setup(10, 10, ships);

    List<Coord> shots = ai.takeShots();
    ai.successfulHits(shots);

    for (Coord c : shots) {
      assertTrue(ai.getHeatmap()[c.y][c.x].isHit());
    }
  }

  /**
   * Tests the end game method
   */
  @Test
  void testAiPlayerEndGame() {
    AiPlayer ai = new AiPlayer();
    ai.endGame(GameResult.WIN, "Test Reason");
  }

  /**
   * tests the get board method
   */
  @Test
  void testAiPlayerGetBoard() {
    AiPlayer ai = new AiPlayer();
    Map<ShipType, Integer> ships = new HashMap<>();
    ships.put(ShipType.CARRIER, 1);
    ships.put(ShipType.BATTLESHIP, 1);
    ships.put(ShipType.DESTROYER, 1);
    ships.put(ShipType.SUBMARINE, 1);
    ai.setup(10, 10, ships);

    String board = ai.getBoard();

    assertNotNull(board);
  }
}
