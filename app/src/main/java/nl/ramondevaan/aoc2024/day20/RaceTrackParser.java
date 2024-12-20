package nl.ramondevaan.aoc2024.day20;

import nl.ramondevaan.aoc2024.util.Coordinate;
import nl.ramondevaan.aoc2024.util.IntMap;
import nl.ramondevaan.aoc2024.util.Parser;

import java.util.List;

import static nl.ramondevaan.aoc2024.day20.RaceTrack.WALL;

public class RaceTrackParser implements Parser<List<String>, RaceTrack> {
  @Override
  public RaceTrack parse(final List<String> toParse) {
    final var rows = toParse.size();
    final var columns = toParse.getFirst().length();
    final var builder = IntMap.builder(rows, columns);

    Coordinate start = null, end = null;

    for (int row = 0; row < rows; row++) {
      final var chars = toParse.get(row).toCharArray();
      for (int column = 0; column < columns; column++) {
        switch (chars[column]) {
          case '#' -> builder.set(row, column, WALL);
          case '.' -> {
          }
          case 'S' -> start = new Coordinate(row, column);
          case 'E' -> end = new Coordinate(row, column);
          default -> throw new IllegalStateException("Unexpected character: " + chars[column]);
        }
      }
    }

    return new RaceTrack(builder.build(), start, end);
  }
}
