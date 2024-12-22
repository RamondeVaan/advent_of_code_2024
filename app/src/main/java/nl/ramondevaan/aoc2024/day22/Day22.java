package nl.ramondevaan.aoc2024.day22;

import com.google.common.primitives.ImmutableLongArray;
import nl.ramondevaan.aoc2024.day22.WindowSpliterator.PriceChanges;

import java.util.Arrays;
import java.util.List;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Day22 {

  private final static int SIZE = 2000;
  private final static int WINDOW_SIZE = 4;
  private final static int SKIP = WINDOW_SIZE - 1;
  private final static int LIMIT = SIZE - WINDOW_SIZE;
  private final static int RESULT_SIZE = 1 << (5 * WINDOW_SIZE);
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
    final long[] result = new long[RESULT_SIZE];
    initialSecretNumbers.stream().boxed()
        .flatMap(Day22::priceChanges)
        .forEach(priceChanges -> result[priceChanges.changes()] += priceChanges.price());
    return Arrays.stream(result).max().orElseThrow();
  }

  private static Stream<PriceChanges> priceChanges(final long secret) {
    return StreamSupport.stream(new WindowSpliterator(secret, WINDOW_SIZE, Day22::nextSecret), false)
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
