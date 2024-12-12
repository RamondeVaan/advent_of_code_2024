package nl.ramondevaan.aoc2024.day10;

import nl.ramondevaan.aoc2024.util.*;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Arrays;
import java.util.List;
import java.util.function.ToLongFunction;

public class Day10 {
  private final static List<Direction> DIRECTIONS = Arrays.stream(Direction.values()).toList();

  private final IntMap map;

  public Day10(final List<String> lines) {
    final var parser = new IntMapParser('0');
    this.map = parser.parse(lines);
  }

  public long solve1() {
    return solve(trailHead -> score(trailHead.coordinate(), 0, IntMap.builder(map.rows(), map.columns())));
  }

  public long solve2() {
    return solve(trailHead -> rating(trailHead.coordinate(), 0));
  }

  public long solve(final ToLongFunction<IntMapEntry> scoreFunction) {
    return map.streamEntries()
        .filter(entry -> entry.value() == 0)
        .mapToLong(scoreFunction)
        .sum();
  }

  private long score(final Coordinate position, final int value, final IntMap.Builder visited) {
    return DIRECTIONS.stream()
        .map(d -> d.apply(position))
        .filter(map::contains)
        .filter(p -> !visited.hasFlag(p, 1))
        .map(p -> ImmutablePair.of(p, map.valueAt(p)))
        .filter(p -> p.right - value == 1)
        .peek(p -> visited.flag(p.left, 1))
        .mapToLong(p -> p.right == 9 ? 1 : score(p.left, p.right, visited))
        .sum();
  }

  private long rating(final Coordinate position, final int value) {
    return DIRECTIONS.stream()
        .map(d -> d.apply(position))
        .filter(map::contains)
        .map(p -> ImmutablePair.of(p, map.valueAt(p)))
        .filter(p -> p.right - value == 1)
        .mapToLong(p -> p.right == 9 ? 1 : rating(p.left, p.right))
        .sum();
  }
}
