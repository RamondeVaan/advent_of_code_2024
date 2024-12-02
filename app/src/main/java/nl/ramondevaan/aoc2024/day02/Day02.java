package nl.ramondevaan.aoc2024.day02;

import com.google.common.primitives.ImmutableIntArray;

import java.util.List;

public class Day02 {

  private final List<ImmutableIntArray> reports;

  public Day02(final List<String> lines) {
    final var parser = new ReportParser();
    this.reports = lines.stream().map(parser::parse).toList();
  }

  public long solve1() {
    return reports.stream().filter(report -> isSafe2(report, 0)).count();
  }

  public long solve2() {
    return reports.stream()
        .filter(report -> isSafe2(report, 1))
        .count();
  }

  private boolean isSafe2(final ImmutableIntArray levels, final int tolerance) {
    return isSafe(levels, levels.get(0), 1, 0, tolerance) ||
        (tolerance > 0 && isSafe(levels, levels.get(1), 2, 0, tolerance - 1));
  }

  private boolean isSafe(final ImmutableIntArray levels, final int last, final int index,
      final int lastDifference, final int tolerance) {
    if (index >= levels.length()) {
      return true;
    }

    final int current = levels.get(index);
    final int difference = current - last;
    final int absDifference = Math.abs(difference);

    boolean result = true;
    if (absDifference < 1 || absDifference > 3) {
      result = false;
    } else if (difference * lastDifference < 0) {
      result = false;
    }

    return (result && isSafe(levels, current, index + 1, difference, tolerance)) ||
        (tolerance > 0 && isSafe(levels, last, index + 1, lastDifference, tolerance - 1));
  }
}
