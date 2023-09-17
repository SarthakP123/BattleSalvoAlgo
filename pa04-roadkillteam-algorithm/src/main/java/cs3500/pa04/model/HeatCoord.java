package cs3500.pa04.model;

/**
 * Represents a HeatCord for our Ai to peform better
 */
public class HeatCoord extends Coord {
  private HeatCoord up;
  private HeatCoord down;
  private HeatCoord left;
  private HeatCoord right;
  private float probability;

  /**
   * The constructor to initialize the fields
   *
   * @param x represents the x coordinate
   * @param y represents the y coordinate
   */
  public HeatCoord(int x, int y, float probability) {
    super(x, y);
    this.probability = probability;
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

  /**
   * Gets the probability of the coordinate
   *
   * @return the probability of the coordinate
   */
  public float getProbability() {
    return this.probability;
  }

  /**
   * To link the coordinate with the top one
   *
   * @param coord the coordinated to link to
   */
  public void linkUp(HeatCoord coord) {
    this.up = coord;
    //this.adjacent.add(coord);
  }

  /**
   * To link the coordinate with the down one
   *
   * @param coord the coordinate to link to
   */
  public void linkDown(HeatCoord coord) {
    this.down = coord;
    //this.adjacent.add(coord);
  }

  /**
   * To link the coordinate with the left one
   *
   * @param coord the coordinate to link to
   */
  public void linkLeft(HeatCoord coord) {
    this.left = coord;
    //this.adjacent.add(coord);
  }

  /**
   * To link the coordinate with the right one
   *
   * @param coord the coordinate to link to
   */
  public void linkRight(HeatCoord coord) {
    this.right = coord;
    //this.adjacent.add(coord);
  }

  public void reset(int probability) {
    this.probability = probability;
  }

  public void zones(HitZone zone, ShipDirection direction) {
    if (direction.equals(ShipDirection.VERTICAL)) {
      HeatCoord nextUp = this.up;
      for (int i = 0; i < 6; i++) {
        if (nextUp == null || nextUp.isHit() && !nextUp.occupied()) {
          break;
        }
        zone.add(nextUp);
        nextUp = nextUp.up;
      }
      HeatCoord nextDown = this.down;
      for (int i = 0; i < 6; i++) {
        if (nextDown == null || nextDown.isHit() && !nextDown.occupied()) {
          break;
        }
        zone.add(nextDown);
        nextDown = nextDown.down;
      }
    } else if (direction.equals(ShipDirection.HORIZONTAL)) {
      HeatCoord nextLeft = this.left;
      for (int i = 0; i < 6; i++) {
        if (nextLeft == null || nextLeft.isHit() && !nextLeft.occupied()) {
          break;
        }
        zone.add(nextLeft);
        nextLeft = nextLeft.left;
      }
      HeatCoord nextRight = this.right;
      for (int i = 0; i < 6; i++) {
        if (nextRight == null || nextRight.isHit() && !nextRight.occupied()) {
          break;
        }
        zone.add(nextRight);
        nextRight = nextRight.right;
      }
    }
  }
}
