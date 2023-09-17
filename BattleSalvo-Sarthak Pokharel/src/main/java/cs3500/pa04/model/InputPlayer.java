package cs3500.pa04.model;

import cs3500.pa04.View.GameView;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * A human player
 */
public class InputPlayer extends AbstractPlayer {

  private final GameView view;
  private final String name;
  private Coord[][] oppBoard;

  /**
   * Constructor for unseeded random values
   *
   * @param view user prompt class
   */
  public InputPlayer(GameView view) {
    super(new Random());
    this.view = view;
    this.name = this.view.promptName();
  }

  /**
   * Constructor for provided random object (probably seeded)
   *
   * @param view user prompt class
   */
  public InputPlayer(GameView view, Random rng) {
    super(rng);
    this.view = view;
    this.name = this.view.promptName();
  }

  /**
   * Get the player's name.
   *
   * @return the player's name
   */
  @Override
  public String name() {
    return this.name;
  }

  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    this.oppBoard = new Coord[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        this.oppBoard[i][j] = new Coord(j, i);
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
    int aliveShips = this.aliveShips();
    this.view.showBoard(this.board, this.oppBoard);
    ArrayList<Coord> shots = new ArrayList<>();
    int[][] salvoLocations =
        this.view.promptSalvo(aliveShips, this.oppBoard.length, this.oppBoard[0].length);
    for (int[] salvoLocation : salvoLocations) {
      Coord tile = this.oppBoard[salvoLocation[0]][salvoLocation[1]];
      tile.doHit();
      shots.add(tile);
    }
    return shots;
  }

  /**
   * Notifies the player that the game is over.
   * Win, lose, and draw should all be supported
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  @Override
  public void endGame(GameResult result, String reason) {
    if (result.equals(GameResult.WIN)) {
      System.out.println("You win!");
    } else if (result.equals(GameResult.LOSE)) {
      System.out.println("You lost!");
    } else if (result.equals(GameResult.DRAW)) {
      System.out.println("Draw!");
    }
    System.out.println(reason);
  }

  /**
   * @return the opponent's board from this player's knowledge
   */
  public Coord[][] getOppBoard() {
    return this.oppBoard;
  }

}
