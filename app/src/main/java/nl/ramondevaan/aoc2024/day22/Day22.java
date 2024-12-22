package nl.ramondevaan.aoc2024.day22;

import com.google.common.primitives.ImmutableLongArray;

import java.util.Arrays;
import java.util.List;
import java.util.stream.LongStream;

public class Day22 {

  private final static int SIZE = 2000;
  private final static int WINDOW_SIZE = 4;
  private final static int SKIP = WINDOW_SIZE - 1;
  private final static int LIMIT = SIZE - WINDOW_SIZE;
  private final static int RESULT_SIZE = 1 << (5 * WINDOW_SIZE);
  private final static int MASK = RESULT_SIZE - 1;
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
    final var result = new int[RESULT_SIZE];
    final var visited = new boolean[RESULT_SIZE];
    var max = Integer.MIN_VALUE;

    for (int j = 0; j < initialSecretNumbers.length(); j++) {
      final var secret = initialSecretNumbers.get(j);
      Arrays.fill(visited, false);
      var last = secret;
      int changes = 0, lastBananas = (int) (secret % 10);
      for (int i = 0; i < SKIP; i++)
        changes = ((changes << 5) & MASK) + (9 - lastBananas + (lastBananas = (int) (last = nextSecret(last)) % 10));
      for (int i = 0; i < LIMIT; i++) {
        changes = ((changes << 5) & MASK) + (9 - lastBananas + (lastBananas = (int) (last = nextSecret(last)) % 10));
        if (!visited[changes]) {
          max = Math.max(result[changes] += lastBananas, max);
          visited[changes] = true;
        }
      }
    }

    return max;
  }

  public static long nextSecret(long secret) {
    secret = (secret ^ (secret << 6)) & 16777215;
    secret = (secret ^ (secret >>> 5)) & 16777215;
    return (secret ^ (secret << 11)) & 16777215;
  }
}
