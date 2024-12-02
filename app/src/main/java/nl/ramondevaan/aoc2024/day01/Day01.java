package nl.ramondevaan.aoc2024.day01;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day01 {

  private final LocationIdLists locationIdLists;

  public Day01(final List<String> lines) {
    final var parser = new LocationidListsParser();
    this.locationIdLists = parser.parse(lines);
  }

  public long solve1() {
    final var leftSorted = locationIdLists.left().stream().sorted().toArray();
    final var rightSorted = locationIdLists.right().stream().sorted().toArray();

    return IntStream.range(0, leftSorted.length)
            .map(i -> Math.abs(leftSorted[i] - rightSorted[i]))
            .sum();
  }

  public long solve2() {
    final var occurrence = locationIdLists.right().stream().boxed()
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    return locationIdLists.left().stream()
            .mapToLong(locationId -> locationId * occurrence.getOrDefault(locationId, 0L))
            .sum();
  }
}
