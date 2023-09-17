package cs3500.pa04.model;

/**
 * ShipType represents the different ships and their corresponding size
 */
public enum ShipType {
  CARRIER(6),
  BATTLESHIP(5),
  DESTROYER(4),
  SUBMARINE(3),
  UNKNOWN(0);

  private final int size;

  /**
   * Represents the size of the ship
   *
   * @param size how big the ship is
   */
  ShipType(int size) {
    this.size = size;
  }

  /**
   * Gets the size of the ship
   *
   * @return the size of the ship
   */
  public int getSize() {
    return size;
  }
}
