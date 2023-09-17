package cs3500.pa04.controller;

import cs3500.pa04.model.Coord;
import cs3500.pa04.model.GameResult;
import cs3500.pa04.model.Player;
import cs3500.pa04.model.Ship;
import cs3500.pa04.model.ShipType;
import cs3500.pa04.View.GameView;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller implementation for battleSalvo
 */
public class LocalGameController {
  private final GameView view;
  private final Player p1;
  private final Player p2;
  private final List<Ship> p1Ships;
  private final List<Ship> p2Ships;

  /**
   * Constructor for a human versus computer game of BattleSalvo
   *
   * @param view user interaction source
   * @param p1   human player
   * @param p2   computer player
   */
  public LocalGameController(GameView view, Player p1, Player p2) {
    this.view = view;
    this.p1 = p1;
    this.p2 = p2;
    System.out.println("SETUP");
    int[] boardSize = this.view.promptBoardSize(6, 15);

    int[] shipCounts = this.view.promptShipCounts(Math.min(boardSize[0], boardSize[1]));
    Map<ShipType, Integer> map = new HashMap<>();
    map.put(ShipType.CARRIER, shipCounts[0]);
    map.put(ShipType.BATTLESHIP, shipCounts[1]);
    map.put(ShipType.DESTROYER, shipCounts[2]);
    map.put(ShipType.SUBMARINE, shipCounts[3]);

    this.p1Ships = p1.setup(boardSize[0], boardSize[1], map);
    this.p2Ships = p2.setup(boardSize[0], boardSize[1], map);
  }

  /**
   * game loop, runs turns and handles winning conditions
   */
  public void run() {
    boolean p1Lose = false;
    boolean p2Lose = false;
    while (true) {
      p1Lose = true;
      for (Ship ship1 : p1Ships) {
        if (!ship1.isSunk()) {
          p1Lose = false;
        }
      }
      p2Lose = true;
      for (Ship ship2 : p2Ships) {
        if (!ship2.isSunk()) {
          p2Lose = false;
        }
      }
      if (p1Lose || p2Lose) {
        break;
      }
      List<Coord> p2Shots = p2.takeShots();
      List<Coord> p1Shots = p1.takeShots();
      p2Shots = p1.reportDamage(p2Shots);
      p1Shots = p2.reportDamage(p1Shots);
      p1.successfulHits(p1Shots);
      p2.successfulHits(p2Shots);
    }
    this.endGame(p1Lose, p2Lose);
  }

  /**
   * Determines if the game is over or not
   * @param p1Lose if player 1 loses
   * @param p2Lose if player 2 loses
   */
  public void endGame(boolean p1Lose, boolean p2Lose) {
    String reason = "";
    GameResult p1Result = GameResult.LOSE;
    GameResult p2Result = GameResult.LOSE;
    if (p1Lose && p2Lose) {
      reason = "Both players have no ships remaining.";
      p1Result = GameResult.DRAW;
      p2Result = GameResult.DRAW;
    } else if (p1Lose) {
      reason = p1.name() + " has no ships remaining";
      p2Result = GameResult.WIN;
    } else if (p2Lose) {
      reason = p2.name() + " has no ships remaining";
      p1Result = GameResult.WIN;
    }
    p1.endGame(p1Result, reason);
    p2.endGame(p2Result, reason);
  }
}
