package nl.ramondevaan.aoc2024.day04;

import nl.ramondevaan.aoc2024.util.BlankStringPartitioner;
import nl.ramondevaan.aoc2024.util.IntMap;

import java.util.List;

public class Day04 {

  private final IntMap wordSearch;
  private final List<IntMap> patterns01;
  private final List<IntMap> patterns02;

  public Day04(final List<String> lines) {
    final var wordSearchParser = new WordSearchParser();
    final var partitioner = new BlankStringPartitioner();
    final var patternParser = new PatternParser(partitioner, wordSearchParser);

    this.wordSearch = wordSearchParser.parse(lines);
    this.patterns01 = patternParser.parse(Day04.class.getResource("/day_04_01.txt"));
    this.patterns02 = patternParser.parse(Day04.class.getResource("/day_04_02.txt"));
  }

  public long solve1() {
    return solve(patterns01);
  }

  public long solve2() {
    return solve(patterns02);
  }

  private long solve(final List<IntMap> patterns) {
    return patterns.stream()
        .mapToLong(pattern -> countOccurrences(wordSearch, pattern))
        .sum();
  }

  private static int countOccurrences(final IntMap wordSearch, final IntMap pattern) {
    final var maxRow = wordSearch.rows() - pattern.rows();
    final var maxColumn = wordSearch.columns() - pattern.columns();
    var sum = 0;

    for (int startRow = 0; startRow <= maxRow; startRow++) {
      next:
      for (int startColumn = 0; startColumn <= maxColumn; startColumn++) {
        for (int row = 0; row < pattern.rows(); row++) {
          for (int column = 0; column < pattern.columns(); column++) {
            int patternCharacter = pattern.valueAt(row, column);
            if (patternCharacter != '.' &&
                patternCharacter != wordSearch.valueAt(startRow + row, startColumn + column)) {
              continue next;
            }
          }
        }
        sum++;
      }
    }

    return sum;
  }
}
