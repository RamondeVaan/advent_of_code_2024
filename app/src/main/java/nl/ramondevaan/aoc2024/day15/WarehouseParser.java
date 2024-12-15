package nl.ramondevaan.aoc2024.day15;

import nl.ramondevaan.aoc2024.util.Coordinate;
import nl.ramondevaan.aoc2024.util.IntMap;
import nl.ramondevaan.aoc2024.util.Parser;

import java.util.List;

public class WarehouseParser implements Parser<List<String>, Warehouse> {
  @Override
  public Warehouse parse(final List<String> toParse) {
    final var rows = toParse.size();
    final var columns = toParse.getFirst().length();

    final var builder = IntMap.builder(rows, columns);
    Coordinate robot = null;

    for (int row = 0; row < rows; row++) {
      final var chars = toParse.get(row).toCharArray();
      for (int column = 0; column < columns; column++) {
        switch (chars[column]) {
          case '#' -> builder.set(row, column, Warehouse.WALL);
          case 'O' -> builder.set(row, column, Warehouse.BOX);
          case '@' -> robot = new Coordinate(row, column);
        }
      }
    }

    return new Warehouse(builder.build(), robot);
  }
}
