package nl.ramondevaan.aoc2024.day21;

import java.util.List;

public class Day21 {

  private final List<Code> codes;
  private final NumericKeypad numericKeypad;
  private final DirectionalKeypad directionalKeypad;

  public Day21(final List<String> lines) {
    final var parser = new CodeParser();
    this.codes = lines.stream().map(parser::parse).toList();
    this.numericKeypad = new NumericKeypad();
    this.directionalKeypad = new DirectionalKeypad();
  }

  public long solve1() {
    return solve(2);
  }

  public long solve2() {
    return solve(25);
  }

  final long solve(final int numberOfKeypads) {
    final var size = NumericKeypadButton.values().length;
    final var cache = new Long[size][size][numberOfKeypads + 1];

    return codes.stream()
        .mapToLong(code -> complexity(code, expandAndCount(code.buttons(), numericKeypad, numberOfKeypads, NumericKeypadButton.A, cache)))
        .sum();
  }

  private long expandAndCount(final List<KeypadButton> code, final Keypad keypad, final int remaining, KeypadButton last, final Long[][][] cache) {
    if (remaining < 0) return code.size();
    long ret = 0L;
    Long cacheValue;

    for (final var button : code) {
      if ((cacheValue = cache[last.ordinal()][button.ordinal()][remaining]) == null) {
        cacheValue = keypad.getRobotActions(last.ordinal(), button.ordinal())
            .mapToLong(actions -> expandAndCount(actions, directionalKeypad, remaining - 1, DirectionalKeypadButton.A, cache))
            .min()
            .orElseThrow();
        cache[last.ordinal()][button.ordinal()][remaining] = cacheValue;
      }
      ret += cacheValue;
      last = button;
    }

    return ret;
  }

  private long complexity(final Code code, final long actions) {
    return code.value() * actions;
  }
}
