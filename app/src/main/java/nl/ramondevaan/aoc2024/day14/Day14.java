package nl.ramondevaan.aoc2024.day14;

import nl.ramondevaan.aoc2024.util.FileIntMapParser;
import nl.ramondevaan.aoc2024.util.IntMap;
import nl.ramondevaan.aoc2024.util.Position;

import java.util.List;
import java.util.stream.Stream;

import static java.lang.Math.floorMod;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class Day14 {

  private static final int WIDTH = 101;
  private static final int WIDTH_MIDDLE = WIDTH / 2;
  private static final int W_DIV = WIDTH_MIDDLE + 1;
  private static final int HEIGHT = 103;
  private static final int HEIGHT_MIDDLE = HEIGHT / 2;
  private static final int H_DIV = HEIGHT_MIDDLE + 1;

  private final IntMap christmasTree;
  private final List<Robot> robots;

  public Day14(final List<String> lines) {
    final var robotParser = new RobotParser();
    final var treeParser = new FileIntMapParser('.');

    this.robots = lines.stream().map(robotParser::parse).toList();
    this.christmasTree = treeParser.parse(Day14.class.getResource("/day_14_02.txt"));
  }

  public long solve1() {
    final var quadrantCount = move(100)
        .filter(p -> p.x() != WIDTH_MIDDLE && p.y() != HEIGHT_MIDDLE)
        .collect(groupingBy(p -> new Position(p.x() / W_DIV, p.y() / H_DIV), counting()));

    return quadrantCount.values().stream()
        .mapToLong(Long::longValue)
        .reduce(1L, (left, right) -> left * right);
  }

  public long solve2() {
    for (int i = 0; i < 10_000; i++) {
      final var builder = IntMap.builder(HEIGHT, WIDTH);
      move(i).forEach(lp -> builder.set(lp.y(), lp.x(), 1));
      if (builder.contains(christmasTree)) {
        return i;
      }
      builder.fill(0);
    }

    return -1L;
  }

  private Stream<Position> move(final int seconds) {
    return robots.stream()
        .map(r -> new Position(floorMod(r.px() + r.vx() * seconds, WIDTH), floorMod(r.py() + r.vy() * seconds, HEIGHT)));
  }
}
