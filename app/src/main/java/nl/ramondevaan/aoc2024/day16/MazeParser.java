package nl.ramondevaan.aoc2024.day16;

import nl.ramondevaan.aoc2024.util.Coordinate;
import nl.ramondevaan.aoc2024.util.IntMap;
import nl.ramondevaan.aoc2024.util.Parser;

import java.util.List;

public class MazeParser implements Parser<List<String>, Maze> {
  @Override
  public Maze parse(final List<String> toParse) {
    final var rows = toParse.size();
    final var columns = toParse.getFirst().length();

    final var builder = IntMap.builder(rows, columns);
    Coordinate start = null, end = null;

    for (int row = 0; row < rows; row++) {
      final var chars = toParse.get(row).toCharArray();
      for (int column = 0; column < columns; column++) {
        switch (chars[column]) {
          case '#' -> builder.set(row, column, Maze.WALL);
          case '.' -> builder.set(row, column, Maze.SPACE);
          case 'S' -> start = new Coordinate(row, column);
          case 'E' -> end = new Coordinate(row, column);
        }
      }
    }

    return new Maze(builder.build(), start, end);
  }
}
