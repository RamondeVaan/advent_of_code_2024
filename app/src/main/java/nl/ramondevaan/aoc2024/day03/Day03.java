package nl.ramondevaan.aoc2024.day03;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day03 {
  private final Pattern MUL_PATTERN = Pattern.compile("mul\\((?<left>\\d+),(?<right>\\d+)\\)");
  private final Pattern ALL_PATTERN = Pattern.compile("mul\\((?<left>\\d+),(?<right>\\d+)\\)|(?<do>do\\(\\))|(?<dont>don't\\(\\))");

  private final String lines;

  public Day03(final List<String> lines) {
    this.lines = String.join("", lines);
  }

  public long solve1() {
    final var matcher = MUL_PATTERN.matcher(lines);

    return Stream.iterate(matcher, Matcher::find, t -> t)
        .mapToLong(m -> Long.parseLong(m.group("left")) * Long.parseLong(m.group("right")))
        .sum();
  }

  public long solve2() {
    final var matcher = ALL_PATTERN.matcher(lines);

    var enabled = true;
    var sum = 0L;

    while (matcher.find()) {
      if (matcher.group("do") != null) {
        enabled = true;
      } else if (matcher.group("dont") != null) {
        enabled = false;
      } else if (enabled) {
        sum += Long.parseLong(matcher.group("left")) * Long.parseLong(matcher.group("right"));
      }
    }

    return sum;
  }
}
