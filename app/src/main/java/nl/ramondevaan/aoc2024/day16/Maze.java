package nl.ramondevaan.aoc2024.day16;

import nl.ramondevaan.aoc2024.util.Coordinate;
import nl.ramondevaan.aoc2024.util.IntMap;

public record Maze(IntMap map, Coordinate startPosition, Coordinate endPosition) {

  public static final int WALL = -1;
  public static final int SPACE = 0;

}
