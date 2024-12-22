package nl.ramondevaan.aoc2024.day22;

import com.google.common.primitives.ImmutableLongArray;
import nl.ramondevaan.aoc2024.day22.WindowSpliterator.Price;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Day22 {

  private final static int SIZE = 2000;
  private final static int DEQUE_SIZE = 4;
  private final static int SKIP = DEQUE_SIZE - 1;
  private final static int LIMIT = SIZE - DEQUE_SIZE;
  private final ImmutableLongArray initialSecretNumbers;

  public Day22(final List<String> lines) {
    final var builder = ImmutableLongArray.builder();
    lines.stream().map(Long::parseLong).forEach(builder::add);
    this.initialSecretNumbers = builder.build();
  }

  public long solve1() {
    return initialSecretNumbers.stream()
        .map(secret -> LongStream.iterate(secret, Day22::nextSecret).limit(2001)
            .reduce((l, r) -> r).orElseThrow())
        .sum();
  }

  public long solve2() {
    final var collect = initialSecretNumbers.stream().boxed()
        .flatMap(Day22::best)
        .collect(Collectors.groupingBy(Price::changes, Collectors.summingLong(Price::price)));
    return collect.values().stream().mapToLong(Long::longValue).max().orElseThrow();
  }

  private static Stream<Price> best(final long secret) {
    return StreamSupport.stream(new WindowSpliterator(secret, DEQUE_SIZE, Day22::nextSecret), false)
        .skip(SKIP)
        .limit(LIMIT)
        .distinct();
  }

  private static long nextSecret(long secret) {
    secret = (secret ^ (secret << 6)) & 16777215;
    secret = (secret ^ (secret >>> 5)) & 16777215;
    return (secret ^ (secret << 11)) & 16777215;
  }
}
