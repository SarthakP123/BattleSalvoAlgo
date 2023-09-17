package cs3500.pa04.oldmodel;

import cs3500.pa04.model.Coord;
import cs3500.pa04.model.HeatCoord;
import cs3500.pa04.model.ShipType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a HeatCord for our Ai to peform better
 */
public class OldHeatCoord extends Coord {
  private OldHeatCoord up;
  private OldHeatCoord down;
  private OldHeatCoord left;
  private OldHeatCoord right;
  private float probability;

  private Map<ShipType, Float> table = new HashMap<>();

  /**
   * The constructor to initialize the fields
   *
   * @param x represents the x coordinate
   * @param y represents the y coordinate
   */
  public OldHeatCoord(int x, int y, float probability) {
    super(x, y);
    this.probability = probability;
    this.table.put(ShipType.CARRIER, probability);
    this.table.put(ShipType.BATTLESHIP, probability);
    this.table.put(ShipType.DESTROYER, probability);
    this.table.put(ShipType.SUBMARINE, probability);
  }

  /**
   * To shot adjacent coordinates by increasing probability
   */
  private void adjacentHit(float prob) {
    if (!this.isHit()) {
      this.probability += prob;
      //this.recalculate();
      //parityCoords.add(this);
    }
  }

  /**
   * To link the coordinate with the top one
   *
   * @param coord the coordinated to link to
   */
  public void linkUp(OldHeatCoord coord) {
    this.up = coord;
    //this.adjacent.add(coord);
  }

  /**
   * To link the coordinate with the down one
   *
   * @param coord the coordinate to link to
   */
  public void linkDown(OldHeatCoord coord) {
    this.down = coord;
    //this.adjacent.add(coord);
  }

  /**
   * To link the coordinate with the left one
   *
   * @param coord the coordinate to link to
   */
  public void linkLeft(OldHeatCoord coord) {
    this.left = coord;
    //this.adjacent.add(coord);
  }

  /**
   * To link the coordinate with the right one
   *
   * @param coord the coordinate to link to
   */
  public void linkRight(OldHeatCoord coord) {
    this.right = coord;
    //this.adjacent.add(coord);
  }

  /**
   * Recalculate after we get a hit
   */
  public void recalculate(Map<ShipType, Integer> spec, int turn) {
    if (this.isHit()) {
      return;
    }
    int likelyV = 0;
    int likelyH = 0;
    int marginUp = 0;
    int marginDown = 0;
    int marginLeft = 0;
    int marginRight = 0;
    OldHeatCoord nextUp = this.up;
    for (int i = 0; i < 6; i++) {
      if (nextUp == null || nextUp.isHit() && !nextUp.occupied()) {
        break;
      }
      if (nextUp.occupied()) {
        likelyV++;
      }
      marginUp++;
      nextUp = nextUp.up;
    }
    OldHeatCoord nextDown = this.down;
    for (int i = 0; i < 6; i++) {
      if (nextDown == null || nextDown.isHit() && !nextDown.occupied()) {
        break;
      }
      if (nextDown.occupied()) {
        likelyV++;
      }
      marginDown++;
      nextDown = nextDown.down;
    }
    OldHeatCoord nextLeft = this.left;
    for (int i = 0; i < 6; i++) {
      if (nextLeft == null || nextLeft.isHit() && !nextLeft.occupied()) {
        break;
      }
      if (nextLeft.occupied()) {
        likelyH++;
      }
      marginLeft++;
      nextLeft = nextLeft.left;
    }
    OldHeatCoord nextRight = this.right;
    for (int i = 0; i < 6; i++) {
      if (nextRight == null || nextRight.isHit() && !nextRight.occupied()) {
        break;
      }
      if (nextRight.occupied()) {
        likelyH++;
      }
      marginRight++;
      nextRight = nextRight.right;
    }
    int ships = 0;
    ships += marginUp > 1 ? marginUp - 1 : 0;
    ships += marginDown > 1 ? marginDown - 1 : 0;
    ships += marginLeft > 1 ? marginLeft - 1 : 0;
    ships += marginRight > 1 ? marginRight - 1 : 0;

    if (marginUp > 0 && marginDown > 0 || marginLeft > 0 && marginRight > 0) {
      ships++;
    }

    int carrier = likelyH + likelyV;
    int battleship = likelyH + likelyV;
    int destroyer = likelyH + likelyV;
    int submarine = likelyH + likelyV;

    for (int i = 0; i < 6; i++) {
      //horizontal
      if (marginLeft >= i && marginRight >= 5 - i) {
        carrier++; //could have a carrier
      }
      if (marginLeft >= i && marginRight >= 4 - i) {
        battleship++; //could have a battleship
      }
      if (marginLeft >= i && marginRight >= 3 - i) {
        destroyer++; //could have a destroyer
      }
      if (marginLeft >= i && marginRight >= 2 - i) {
        submarine++; //could have a submarine
      }
      //vertical
      if (marginUp >= i && marginDown >= 5 - i) {
        carrier++; //could have a carrier
      }
      if (marginUp >= i && marginDown >= 4 - i) {
        battleship++; //could have a battleship
      }
      if (marginUp >= i && marginDown >= 3 - i) {
        destroyer++; //could have a destroyer
      }
      if (marginUp >= i && marginDown >= 2 - i) {
        submarine++; //could have a submarine
      }
    }
    int total = 0;
    for (int value : spec.values()) {
      total += value;
    }
    this.table.put(ShipType.CARRIER, (float) carrier * spec.get(ShipType.CARRIER) / total);
    this.table.put(ShipType.BATTLESHIP, (float) battleship * spec.get(ShipType.BATTLESHIP) / total);
    this.table.put(ShipType.DESTROYER, (float) destroyer * spec.get(ShipType.DESTROYER) / total);
    this.table.put(ShipType.SUBMARINE, (float) submarine * spec.get(ShipType.SUBMARINE) / total);

    this.probability +=
        Math.max(Math.max(this.table.get(ShipType.CARRIER), this.table.get(ShipType.BATTLESHIP)),
            Math.max(this.table.get(ShipType.DESTROYER), this.table.get(ShipType.SUBMARINE))) *
            turn;
    if (submarine == 0) {
      this.probability = 0;
    }
  }

  /**
   * Don't hit the same one again
   *
   * @return true or false
   */
  @Override
  public boolean doHit() {
    this.probability = 0;
    return super.doHit();
  }

  public void doHit(ArrayList<OldHeatCoord> parity) {
    boolean upcap = this.up == null ? true : this.up.isHit() && !this.up.occupied();
    boolean downcap = this.down == null ? true : this.down.isHit() && !this.down.occupied();
    boolean leftcap = this.left == null ? true : this.left.isHit() && !this.left.occupied();
    boolean rightcap = this.right == null ? true : this.right.isHit() && !this.right.occupied();

    float weight = 1000;

    if (upcap && downcap) {
      OldHeatCoord next = this.left;
      for (int i = 0; i < 5; i++) {
        if (next == null || next.isHit() && !next.occupied()) {
          break;
        }
        next.adjacentHit(weight / 2);
        next = next.left;
      }
      weight = 1000;
      next = this.right;
      for (int i = 0; i < 5; i++) {
        if (next == null || next.isHit() && !next.occupied()) {
          break;
        }
        next.adjacentHit(weight / 2);
        next = next.right;
      }
    } else if (leftcap && rightcap) {
      OldHeatCoord next = this.down;
      for (int i = 0; i < 5; i++) {
        if (next == null || next.isHit() && !next.occupied()) {
          break;
        }
        next.adjacentHit(weight / 2);
        next = next.down;
      }
      weight = 1000;
      next = this.up;
      for (int i = 0; i < 5; i++) {
        if (next == null || next.isHit() && !next.occupied()) {
          break;
        }
        next.adjacentHit(weight / 2);
        next = next.up;
      }
    } else {
      if (this.up != null) {
        this.up.adjacentHit(1000);
      }
      if (this.down != null) {
        this.down.adjacentHit(1000);
      }
      if (this.left != null) {
        this.left.adjacentHit(1000);
      }
      if (this.right != null) {
        this.right.adjacentHit(1000);
      }
    }
    //return super.doHit();
  }

  /**
   * Gets the probability of the coordinate
   *
   * @return the probability of the coordinate
   */
  public float getProbability() {
    return this.probability;
  }
}
