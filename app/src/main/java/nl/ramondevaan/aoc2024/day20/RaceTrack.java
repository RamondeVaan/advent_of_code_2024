package nl.ramondevaan.aoc2024.day20;

import nl.ramondevaan.aoc2024.util.Coordinate;
import nl.ramondevaan.aoc2024.util.IntMap;

public record RaceTrack(IntMap map, Coordinate start, Coordinate end) {

  public static final int WALL = -1;
}
