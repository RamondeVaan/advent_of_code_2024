package nl.ramondevaan.aoc2024.day06;

import lombok.RequiredArgsConstructor;
import nl.ramondevaan.aoc2024.util.Coordinate;
import nl.ramondevaan.aoc2024.util.Direction;
import nl.ramondevaan.aoc2024.util.IntMap;
import nl.ramondevaan.aoc2024.util.Parser;

import java.util.List;

@RequiredArgsConstructor
public class MapParser implements Parser<List<String>, Map> {

  private final int obstruction;

  @Override
  public Map parse(final List<String> toParse) {
    final var builder = IntMap.builder(toParse.size(), toParse.get(0).length());
    Coordinate position = null;

    for (int row = 0; row < toParse.size(); row++) {
      final var line = toParse.get(row).toCharArray();
      for (int column = 0; column < line.length; column++) {
        final var value = switch (line[column]) {
          case '.' -> 0;
          case '^' -> {
            position = Coordinate.of(row, column);
            yield 0;
          }
          case '#' -> obstruction;
          default -> throw new IllegalArgumentException("Invalid character: " + line[column]);
        };
        builder.set(row, column, value);
      }
    }

    return new Map(builder.build(), position, Direction.NORTH);
  }
}
