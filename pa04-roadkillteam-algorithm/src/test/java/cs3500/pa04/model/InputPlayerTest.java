package cs3500.pa04.model;

import cs3500.pa04.View.GameView;
import java.util.LinkedHashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;
import java.util.*;
import static org.mockito.Mockito.when;

  public class InputPlayerTest {
    private GameView mockView;
    private InputPlayer player;
    private String playerName = "TestPlayer";
    private Map<ShipType, Integer> ships;


    @BeforeEach
    public void setup() {
      mockView = Mockito.mock(GameView.class);
      when(mockView.promptName()).thenReturn(playerName);
      player = new InputPlayer(mockView, new Random());
      ships =new LinkedHashMap<>();
      ships.put(ShipType.CARRIER, 1);
      ships.put(ShipType.BATTLESHIP, 2);
      ships.put(ShipType.DESTROYER, 1);
      ships.put(ShipType.SUBMARINE, 1);
    }

    @Test
    public void testName() {
      assertEquals(playerName, player.name());
    }

    @Test
    public void testSetup() {
      List<Ship> setupShips = player.setup(6, 6, ships);
      assertEquals(5, setupShips.size());
      assertNotNull(player.getOppBoard());
    }

    @Test
    public void testGetOppBoard() {
      player.setup(6, 6, new HashMap<>());
      Coord[][] oppBoard = player.getOppBoard();
      assertEquals(6, oppBoard.length);
      assertEquals(6, oppBoard[0].length);
    }
  }
