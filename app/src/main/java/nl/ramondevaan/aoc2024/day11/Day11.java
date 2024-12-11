package nl.ramondevaan.aoc2024.day11;

import com.google.common.math.LongMath;
import com.google.common.primitives.ImmutableLongArray;

import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day11 {

  private final ImmutableLongArray stones;

  public Day11(final List<String> lines) {
    final var parser = new StoneParser();
    this.stones = parser.parse(lines.getFirst());
  }

  public long solve1() {
    return solve(25);
  }

  public long solve2() {
    return solve(75);
  }

  private long solve(final int blinks) {
    Map<Long, Long> cache = new HashMap<>();
    for (int i = 0; i < stones.length(); i++) {
      cache.put(stones.get(i), 1L);
    }

    for (int i = 0; i < blinks; i++) {
      cache = blink(cache);
    }

    return cache.values().stream().mapToLong(Long::longValue).sum();
  }

  private Map<Long, Long> blink(final Map<Long, Long> stones) {
    final Map<Long, Long> newStones = new HashMap<>(stones.size());

    stones.forEach((stone, count) -> {
      if (stone == 0) {
        newStones.merge(1L, count, Long::sum);
        return;
      }
      final var digits = LongMath.log10(stone, RoundingMode.FLOOR) + 1;
      if (digits % 2 == 0) {
        final var div = LongMath.pow(10, digits / 2);
        newStones.merge(stone / div, count, Long::sum);
        newStones.merge(stone % div, count, Long::sum);
        return;
      }
      newStones.merge(stone * 2024L, count, Long::sum);
    });

    return newStones;
  }
}
