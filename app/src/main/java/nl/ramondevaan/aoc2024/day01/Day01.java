package nl.ramondevaan.aoc2024.day01;

import com.google.common.primitives.ImmutableIntArray;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day01 {

  private final ImmutableIntArray leftLocationIdList;
  private final ImmutableIntArray rightLocationIdList;

  public Day01(final List<String> lines) {
    final var parser = new LocationidListsParser();
    final var locationIdLists = parser.parse(lines);
    this.leftLocationIdList = locationIdLists.left;
    this.rightLocationIdList = locationIdLists.right;
  }

  public long solve1() {
    final var leftSorted = leftLocationIdList.stream().sorted().toArray();
    final var rightSorted = rightLocationIdList.stream().sorted().toArray();

    return IntStream.range(0, leftSorted.length)
            .map(i -> Math.abs(leftSorted[i] - rightSorted[i]))
            .sum();
  }

  public long solve2() {
    final var occurrence = rightLocationIdList.stream().boxed()
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    return leftLocationIdList.stream()
            .mapToLong(locationId -> locationId * occurrence.getOrDefault(locationId, 0L))
            .sum();
  }
}
