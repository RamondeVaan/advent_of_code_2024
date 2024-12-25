package nl.ramondevaan.aoc2024.day25;

import java.util.List;

public class Day25 {

  private final List<List<Integer>> keys;
  private final List<List<Integer>> locks;

  public Day25(final List<String> lines) {
    final var parser = new KeyAndLockParser();
    final var keysAndLocks = parser.parse(lines);
    this.keys = keysAndLocks.keys();
    this.locks = keysAndLocks.locks();
  }

  public long solve1() {
    var sum = 0L;
    for (final var key : keys) for (final var lock : locks) if (fits(key, lock)) sum++;
    return sum;
  }

  private static boolean fits(final List<Integer> key, final List<Integer> lock) {
    if (key.size() != lock.size()) return false;
    for (int i = 0; i < key.size(); i++) if (key.get(i) + lock.get(i) > 5) return false;
    return true;
  }
}
