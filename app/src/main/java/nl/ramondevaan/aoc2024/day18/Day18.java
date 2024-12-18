package nl.ramondevaan.aoc2024.day18;

import nl.ramondevaan.aoc2024.util.Coordinate;
import nl.ramondevaan.aoc2024.util.CoordinateParser;
import nl.ramondevaan.aoc2024.util.IntMap;

import java.util.ArrayDeque;
import java.util.List;
import java.util.OptionalInt;

public class Day18 {

  private final static int SIZE = 71;
  private final static int SIZ_MINUS_ONE = SIZE - 1;
  private final static Coordinate TARGET = new Coordinate(SIZ_MINUS_ONE, SIZ_MINUS_ONE);
  private final static int PART_1_LIMIT = 1024;

  private final List<Coordinate> bytes;

  public Day18(final List<String> lines) {
    final var parser = new CoordinateParser();
    this.bytes = lines.stream().map(parser::parse).toList();
  }

  public long solve1() {
    return minimumSteps(PART_1_LIMIT).orElseThrow();
  }

  public String solve2() {
    int min = PART_1_LIMIT + 1, max = bytes.size(), limit;

    while (min < max) {
      if (minimumSteps(limit = (min + max) >>> 1).isPresent()) min = limit + 1;
      else max = limit;
    }

    final var blockingByte = bytes.get(min - 1);
    return "%d,%d".formatted(blockingByte.row(), blockingByte.column());
  }

  private OptionalInt minimumSteps(final int limit) {
    final var builder = IntMap.builder(SIZE, SIZE).fill(Integer.MAX_VALUE).set(0, 0, 0);
    bytes.stream().limit(limit).forEach(b -> builder.set(b.column(), b.row(), -1));

    final var queue = new ArrayDeque<Coordinate>();
    var current = new Coordinate(0, 0);

    do {
      if (current.equals(TARGET)) return OptionalInt.of(builder.get(TARGET));
      final var nextDistance = builder.get(current) + 1;
      current.directNeighbors()
          .filter(builder::contains)
          .filter(c -> builder.get(c) > nextDistance)
          .peek(c -> builder.set(c, nextDistance))
          .forEach(queue::add);
    } while ((current = queue.poll()) != null);

    return OptionalInt.empty();
  }
}
