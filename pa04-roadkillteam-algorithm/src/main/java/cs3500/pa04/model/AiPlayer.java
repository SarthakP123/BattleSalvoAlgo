package cs3500.pa04.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;


/**
 * Represents the AbstractPlayers Class which implements the player interface
 */
public class AiPlayer extends AbstractPlayer {

  private int[][] heatmap2;
  private HeatCoord[][] heatmap;
  private final ArrayList<HeatCoord> shootable = new ArrayList<>();
  private final ArrayList<HeatCoord> hits = new ArrayList<>();
  private ArrayList<HitZone> zones = new ArrayList<>();

  /**
   * Represents the AI's constructor
   */
  public AiPlayer() {
    super(new Random());
  }

  /**
   * Represents the AI's constructor but with a seeded Random
   *
   * @param rng used to generate a random number
   */
  public AiPlayer(Random rng) {
    super(rng);
  }

  /**
   * To represent a player
   *
   * @return The name of the player
   */
  @Override
  public String name() {
    return "AI";
  }

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
    this.heatmap = new HeatCoord[height][width];
    this.heatmap2 = new int[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        HeatCoord coord = new HeatCoord(j, i, rng.nextFloat(1, 4));
        if ((j + i + 1) % 3 == 0) {
          coord = new HeatCoord(j, i, rng.nextFloat(5, 10));
        }
        this.heatmap[i][j] = coord;
        this.shootable.add(coord);
      }
    }
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        HeatCoord current = this.heatmap[i][j];
        if (i > 0) {
          current.linkUp(this.heatmap[i - 1][j]);
        }
        if (i < height - 1) {
          current.linkDown(this.heatmap[i + 1][j]);
        }
        if (j > 0) {
          current.linkLeft(this.heatmap[i][j - 1]);
        }
        if (j < width - 1) {
          current.linkRight(this.heatmap[i][j + 1]);
        }
      }
    }
    return super.setup(height, width, specifications);
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public List<Coord> takeShots() {
    //this.getBoard();
    int aliveShips = this.aliveShips();
    ArrayList<HeatCoord> shots = new ArrayList<>();
    for (HitZone zone : this.zones) {
      if (aliveShips == shots.size()) {
        break;
      }
      ArrayList<HeatCoord> next = zone.nextHits(aliveShips - shots.size());
      for (HeatCoord shot : next) {
        if (aliveShips == shots.size()) {
          break;
        }
        if (shootable.contains(shot)) {
          shots.add(shot);
          shootable.remove(shot);
          shot.doHit();
        }
      }
    }
    while (shots.size() < aliveShips && shootable.size() > 0) {
      this.generate();
      for (int i = 0; i < heatmap.length; i++) {
        for (int j = 0; j < heatmap[i].length; j++) {
          heatmap[i][j].reset(heatmap2[i][j]);
        }
      }
      shootable.sort((a, b) -> {
        if (b.getProbability() > a.getProbability()) {
          return 1;
        } else if (b.getProbability() == a.getProbability()) {
          return 0;
        } else {
          return -1;
        }
      });
      HeatCoord shot = shootable.remove(0);
      shots.add(shot);
      shot.doHit();
    }
    return new ArrayList<>(shots);
  }

  /**
   * Reports to this player what shots in their previous volley returned from takeShots()
   * successfully hit an opponent's ship.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    this.zones = new ArrayList<>();
    for (Coord coord : shotsThatHitOpponentShips) {
      HeatCoord resolved = this.heatmap[coord.y][coord.x];
      resolved.setShip(new Ship(ShipType.UNKNOWN));
      resolved.doHit();
      hits.add(resolved);
    }
    for (HeatCoord hit : this.hits) {
      boolean contains = false;
      for (HitZone zone : this.zones) {
        if (zone.contains(hit)) {
          contains = true;
          break;
        }
      }
      if (!contains) {
        zones.add(new HitZone(hit, ShipDirection.VERTICAL));
        zones.add(new HitZone(hit, ShipDirection.HORIZONTAL));
      }
    }
  }

  /**
   * @param gameResult if the player has won, lost, or forced a draw
   * @param reason     the reason for the game ending
   */
  @Override
  public void endGame(GameResult gameResult, String reason) {
    System.out.println(this.getBoard());
    System.out.println(reason);
  }

  private void clearMap() {
    for (int i = 0; i < heatmap2.length; i++) {
      Arrays.fill(heatmap2[i], 0);
    }
  }

  private void generate() {
    this.clearMap();
    for (int shipsize = 6; shipsize >= 3; shipsize--) {
      //horizontal heatmap
      for (int i = 0; i < heatmap.length; i++) {
        for (int lbound = 0; lbound < heatmap[i].length - shipsize; lbound++) {
          boolean valid = true;
          int rbound = lbound + shipsize;
          for (int j = lbound; j <= rbound; j++) {
            if (heatmap[i][j].isHit() && !heatmap[i][j].occupied()) {
              valid = false;
              break;
            }
          }
          if (valid) {
            for (int j = lbound; j <= rbound; j++) {
              heatmap2[i][j]++;
            }
          }
        }
      }
      //vertical heatmap
      for (int j = 0; j < heatmap[0].length; j++) {
        for (int lbound = 0; lbound < heatmap.length - shipsize; lbound++) {
          int rbound = lbound + shipsize;
          boolean valid = true;
          for (int i = lbound; i <= rbound; i++) {
            if (heatmap[i][j].isHit() && !heatmap[i][j].occupied()) {
              valid = false;
              break;
            }
          }
          if (valid) {
            for (int i = lbound; i <= rbound; i++) {
              heatmap2[i][j]++;
            }
          }
        }
      }
    }
  }


  public String getBoard() {
    StringBuilder enemyBoard = new StringBuilder();
    enemyBoard.append("Enemy Board\n");
    StringBuilder selfBoard = new StringBuilder();
    selfBoard.append("Our Board\n");
    for (int i = 0; i < this.board.length; i++) {
      for (int j = 0; j < this.board[i].length; j++) {
        enemyBoard.append(" ").append(this.heatmap[i][j].toString());
        selfBoard.append(" ").append(this.board[i][j].toString());
      }
      enemyBoard.append("\n");
      selfBoard.append("\n");
    }
    System.out.println(enemyBoard);
    //System.out.println(selfBoard);
    return enemyBoard.toString();
  }
}

