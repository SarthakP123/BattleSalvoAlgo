package cs3500.pa04.controller;

import cs3500.pa04.model.AiPlayer;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.GameResult;
import cs3500.pa04.model.GameType;
import cs3500.pa04.model.Ship;
import cs3500.pa04.model.json.CoordRecord;
import cs3500.pa04.model.json.EndMessage;
import cs3500.pa04.model.json.SetupMessage;
import cs3500.pa04.model.json.ShipRecord;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller implementation for a human player vs an AI player
 */
public class TestGameController extends NetGameController {
  public int moves = 0;
  public int tiles;
  public int ships = 0;
  public GameResult result = null;

  /**
   * Constructor
   *
   * @param player AI player
   */
  public TestGameController(AiPlayer player) {
    super(player, GameType.SINGLE);
  }

  /**
   * Constructor
   *
   * @param player AI player
   * @param type   type of game to be played
   */
  public TestGameController(AiPlayer player, GameType type) {
    super(player, type);
  }

  /**
   * Setup actions for the BattleSalvo client
   */
  @Override
  public List<ShipRecord> setup(SetupMessage message) {
    ArrayList<ShipRecord> ships = new ArrayList<>();
    for (Ship ship : player.setup(message.height(), message.width(), message.spec())) {
      ships.add(ship.toRecord());
      this.ships++;
    }
    this.tiles = message.height() * message.width();
    return ships;
  }

  @Override
  public List<CoordRecord> takeShots() {
    moves++;
    ArrayList<CoordRecord> shots = new ArrayList<>();
    for (Coord coord : player.takeShots()) {
      shots.add(coord.toRecord());
    }
    return shots;
  }

  @Override
  public void endGame(EndMessage message) {
    this.result = message.result();
    this.player.endGame(message.result(), message.reason());
  }

  public float score() {
    return (float) this.tiles / ((float) this.ships * (float) this.moves);
  }

  public String game() {
    return this.player.getBoard();
  }
}
