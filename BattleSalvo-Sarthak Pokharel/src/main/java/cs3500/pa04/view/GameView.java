package cs3500.pa04.View;

import cs3500.pa04.model.Coord;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

/**
 * View from the command-line-interface (console)
 */
public class GameView {
  private final Scanner input;
  private Appendable output;

  private static boolean validInt(int validate, int min, int max) {
    return validate < min || validate > max;
  }

  public GameView(Readable input, Appendable output) {
    this.input = new Scanner(input);
    this.output = Objects.requireNonNull(output);
  }

  public void combine(String phrase) {
    try {
      output.append(phrase).append("\n");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public String promptName() {
    combine("Please enter your player name: ");
    return input.nextLine();
  }


  public int[] promptBoardSize(int minDimension, int maxDimension) {
    if (minDimension > maxDimension) {
      throw new IllegalArgumentException(
          "Minimum dimension cannot be larger than maximum dimension!");
    }
    combine("SYSTEM: Game setup");
    System.out.printf(
        "Please enter a valid height and width below (two numbers between %s and %s):\n",
        minDimension,
        maxDimension);
    int height = input.nextInt();
    int width = input.nextInt();
    while (validInt(height, minDimension, maxDimension)
        || validInt(width, minDimension, maxDimension)) {
      System.out.printf(
          "Invalid dimensions! Please enter a valid height and width below "
              + "(two numbers between %s and %s):\n",
          minDimension,
          maxDimension);
      height = input.nextInt();
      width = input.nextInt();
    }
    return new int[] {height, width};
  }

  public int[] promptShipCounts(int maxShipCount) {
    if (maxShipCount < 4) {
      throw new IllegalArgumentException("Max ship count must be greater than or equal to 4!");
    }
    combine("Please enter a valid grouping of ships.");
    int[] values = new int[4];
    for (int i = 0; i < values.length; i++) {
      values[i] = input.nextInt();
    }
    while (true) {
      int sum = 0;
      boolean allValid = true;
      for (int value : values) {
        if (value < 1) {
          allValid = false;
          continue;
        }
        sum = sum + value;
      }
      if (sum <= maxShipCount && allValid) {
        break;
      }
      combine("""
          Invalid Fleet Size!
          Please enter a valid grouping of ships.
          Remember:
          - Order: CARRIER, BATTLESHIP, DESTROYER, SUBMARINE
          - There must be at least one of each ship.
          """);
      for (int i = 0; i < values.length; i++) {
        values[i] = input.nextInt();
      }
    }
    return values;
  }

  public int[][] promptSalvo(int shotCount, int height, int width) {
    int[][] retarray = new int[shotCount][2];
    System.out.printf("Please enter %s coordinates to hit:\n", shotCount);
    for (int i = 0; i < shotCount; i++) {
      int x = input.nextInt();
      int y = input.nextInt();
      while (validInt(x, 0, width - 1) || validInt(y, 0, height - 1)) {
        System.out.printf(
            "Invalid coordinate! "
                + "Please enter an x value between 0 and %s and a y value between 0 and %s\n",
            width - 1, height - 1);
        x = input.nextInt();
        y = input.nextInt();
      }
      retarray[i][0] = x;
      retarray[i][1] = y;
    }
    return retarray;
  }

  /**
   * Displays two game boards in the console
   *
   * @param local    the local player's board
   * @param opponent the opponent's board from the local player's perspective
   */
  public void showBoard(Coord[][] local, Coord[][] opponent) {
    StringBuilder oppBoard = new StringBuilder();
    StringBuilder localBoard = new StringBuilder();
    oppBoard.append("Opponent's Board: \n");
    localBoard.append("Your Board: \n");
    for (int i = 0; i < local.length; i++) {
      for (int j = 0; j < local[0].length; j++) {
        oppBoard.append(" ").append(opponent[i][j].toString());
        localBoard.append(" ").append(local[i][j].toString());
      }
      oppBoard.append("\n");
      localBoard.append("\n");
    }
    combine(oppBoard.toString());
    combine(localBoard.toString());
  }
}
