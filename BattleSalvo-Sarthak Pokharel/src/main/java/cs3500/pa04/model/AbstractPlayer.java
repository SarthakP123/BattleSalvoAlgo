package cs3500.pa04.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Represents the AbstractPlayers Class which implements the player interface
 */
public abstract class AbstractPlayer implements Player {
  protected final Random rng;
  protected Coord[][] board;
  private List<Ship> ships;


  /**
   * Represents the AbstractPlayer's constructor
   */
  public AbstractPlayer() {
    this.rng = new Random();
  }

  /**
   * Represents the AbstractPlayer's constructor
   *
   * @param rng used to generate a random number
   */
  public AbstractPlayer(Random rng) {
    this.rng = rng;
  }

  /**
   * To represent a player
   *
   * @return The name of the player
   */
  @Override
  public abstract String name();

  /**
   * Represents the setup of the ships
   *
   * @param height         the height of the board, range: [6, 15] inclusive
   * @param width          the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should appear on the board
   * @return returns with a list of ships on coordinates that don't overlap
   */
  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    this.board = new Coord[height][width];
    boolean validShipsGenerated = false;
    while (!validShipsGenerated) {
      ships = new ArrayList<>();
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          this.board[i][j] = new Coord(j, i);
        }
      }
      for (Map.Entry<ShipType, Integer> entry : specifications.entrySet()) {
        ShipType shipType = entry.getKey();
        Integer number = entry.getValue();
        for (int i = 0; i < number; i++) {
          ships.add(new Ship(shipType));
        }
      }
      validShipsGenerated = true;
      for (Ship ship : ships) {
        boolean horizontal = rng.nextBoolean();
        ArrayList<Coord> possibleCoords;
        if (horizontal) {
          int beginLeft;
          beginLeft = rng.nextInt(width - ship.getShipType().getSize() + 1);
          possibleCoords = new ArrayList<>();
          int y = rng.nextInt(height);
          for (int i = beginLeft; i < beginLeft + ship.getShipType().getSize(); i++) {
            possibleCoords.add(this.board[y][i]);
          }
        } else {
          int beginTop = rng.nextInt(height - ship.getShipType().getSize() + 1);
          possibleCoords = new ArrayList<>();
          int x = rng.nextInt(width);
          for (int i = beginTop; i < beginTop + ship.getShipType().getSize(); i++) {
            possibleCoords.add(this.board[i][x]);
          }
        }
        boolean emptyCoords = true;
        for (Coord coord : possibleCoords) {
          if (coord.occupied()) {
            emptyCoords = false;
            validShipsGenerated = false;
          }
        }
        if (emptyCoords) {
          ship.setShipCoordinates(possibleCoords);
        }
      }
    }
    return ships;
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public abstract List<Coord> takeShots();

  /**
   * Given the list of shots the opponent has fired on this player's board, report which
   * shots hit a ship on this player's board.
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return a filtered list of the given shots that contain all locations of shots that hit a
   * ship on this board
   */
  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    ArrayList<Coord> hits = new ArrayList<>();
    for (Coord hit : opponentShotsOnBoard) {
      Coord current = this.board[hit.y][hit.x];
      if (current.doHit()) {
        hits.add(hit);
      }
    }
    return hits;
  }

  /**
   * Reports to this player what shots in their previous volley returned from takeShots()
   * successfully hit an opponent's ship.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    for (Coord coord : shotsThatHitOpponentShips) {
      coord.doHit();
      coord.setShip(new Ship(ShipType.UNKNOWN));
    }
  }

  /**
   * @param gameResult if the player has won, lost, or forced a draw
   * @param reason     the reason for the game ending
   */
  public void endGame(GameResult gameResult, String reason) {
  }

  /**
   * Gets the number of ships still unsunk on this instance's board
   *
   * @return number of ships remaining
   */
  protected int aliveShips() {
    int aliveShips = 0;
    for (Ship ship : this.ships) {
      if (!ship.isSunk()) {
        aliveShips++;
      }
    }
    return aliveShips;
  }
}

