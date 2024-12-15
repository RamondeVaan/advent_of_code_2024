package nl.ramondevaan.aoc2024.day15;

import nl.ramondevaan.aoc2024.util.Coordinate;
import nl.ramondevaan.aoc2024.util.IntMap;

public record Warehouse(IntMap map, Coordinate robot) {

  public static final int WALL = -1;
  public static final int EMPTY = 0;
  public static final int BOX = 1;
  public static final int BOX_LEFT = 2;
  public static final int BOX_RIGHT = 3;
}
