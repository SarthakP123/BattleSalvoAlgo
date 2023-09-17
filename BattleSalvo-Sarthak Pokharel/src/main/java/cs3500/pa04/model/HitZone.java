package cs3500.pa04.model;


import java.util.ArrayList;
import java.util.List;

public class HitZone {
  List<HeatCoord> coords = new ArrayList<>();
  ShipDirection direction;

  public HitZone(HeatCoord origin, ShipDirection direction) {
    this.direction = direction;
    origin.zones(this, direction);
    this.coords.add(origin);
  }

  public boolean contains(HeatCoord coord) {
    return this.coords.contains(coord);
  }

  public ArrayList<HeatCoord> nextHits(int limit) {
    limit = 1;
    ArrayList<HeatCoord> hits = this.hits();
    ArrayList<HeatCoord> shots = new ArrayList<>();
    if (hits.size() == 1) {
      int x = 0;
      int y = 0;
      for (HeatCoord coord : hits) {
        x = coord.getX();
        y = coord.getY();
      }
      for (HeatCoord coord : coords) {
        if (coord.getY() == y + 1 || coord.getY() == y - 1) {
          shots.add(coord);
        } else if (coord.getX() == x + 1 || coord.getX() == x - 1) {
          shots.add(coord);
        }
      }
    } else {
      for (HeatCoord coord : coords) {
        if (shots.size() == limit) {
          return shots;
        }
        if (!coord.isHit()) {
          shots.add(coord);
        }
      }
    }
    return shots;
  }

  public void cull() {
    this.arrange();
    boolean leftset = false;
    int left = 0;
    int right = this.coords.size();
    for (int i = 0; i < coords.size() - 1; i++) {
      HeatCoord coord = coords.get(i);
      if (coord.isHit() && !coord.occupied()) {
        if (leftset && left != i - 1) {
          right = i+1;
        } else {
          left = i;
          leftset = true;
        }
      }
    }
  }

  public ShipDirection getDirection() {
    return this.direction;
  }

  public void add(HeatCoord coord) {
    if (!this.coords.contains(coord)) {
      coords.add(coord);
    }
  }

  public ArrayList<HeatCoord> hits() {
    ArrayList<HeatCoord> hits = new ArrayList<>();
    for (HeatCoord coord : coords) {
      if (coord.isHit() && coord.occupied()) {
        hits.add(coord);
      }
    }
    return hits;
  }

  private void arrange() {
    coords.sort((o1, o2) -> {
      if (o1.getX() < o2.getX() || o1.getY() < o2.getY()) {
        return 1;
      }
      return -1;
    });
  }
}
