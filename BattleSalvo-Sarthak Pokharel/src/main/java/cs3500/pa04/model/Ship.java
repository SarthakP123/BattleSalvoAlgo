package cs3500.pa04.model;

import cs3500.pa04.model.json.ShipRecord;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the ship class
 */
public class Ship {
  public final ShipType type;
  private final List<Coord> shipCoordinates;
  /**
   * The constructor of the Ship
   *
   * @param type Enum of the different ship type a ship can be
   */
  public Ship(ShipType type) {
    this.type = type;
    shipCoordinates = new ArrayList<>();
  }

  /**
   * Sets the Ship coordinates with the assigned one
   *
   * @param shipCoordinates The inputted ship coordinated
   */
  public void setShipCoordinates(List<Coord> shipCoordinates) {
    for (Coord coord : shipCoordinates) {
      coord.setShip(this);
    }
    this.shipCoordinates.addAll(shipCoordinates);
  }

  /**
   * Gets the type of ship
   *
   * @return The type the current ship is
   */
  public ShipType getShipType() {
    return type;
  }

  /**
   * gets the ships coordinates
   *
   * @return the list of ship's coordinates
   */
  public List<Coord> getShipCoordinates() {
    return shipCoordinates;
  }

  /**
   * determines if the ship is sunk or not
   *
   * @return the current state of the ship
   */
  public boolean isSunk() {
    for (Coord tile : this.shipCoordinates) {
      if (!tile.isHit()) {
        return false;
      }
    }
    return true;
  }

  /**
   * Represents the record of the ship
   *
   * @return A ship Record
   */
  public ShipRecord toRecord() {
    ShipDirection direction = ShipDirection.HORIZONTAL;
    Coord lowest = this.shipCoordinates.get(0);
    for (int i = 1; i < shipCoordinates.size(); i++) {
      Coord current = shipCoordinates.get(i);
      if (current.x < lowest.x) {
        direction = ShipDirection.HORIZONTAL;
        lowest = current;
      } else if (current.y < lowest.y) {
        direction = ShipDirection.VERTICAL;
        lowest = current;
      } else if (current.x > lowest.x) {
        direction = ShipDirection.HORIZONTAL;
      } else if (current.y > lowest.y) {
        direction = ShipDirection.VERTICAL;
      }
    }
    return new ShipRecord(lowest.toRecord(), this.type.getSize(), direction.name());
  }
}

