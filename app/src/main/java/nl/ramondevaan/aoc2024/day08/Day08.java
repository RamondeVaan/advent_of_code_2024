package nl.ramondevaan.aoc2024.day08;

import nl.ramondevaan.aoc2024.util.CombinatoricsUtils;
import nl.ramondevaan.aoc2024.util.Coordinate;
import nl.ramondevaan.aoc2024.util.IntMap;
import nl.ramondevaan.aoc2024.util.IntMapParser;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Day08 {

  private final IntMap map;
  private final Map<Integer, List<Coordinate>> antennasByFrequency;

  public Day08(final List<String> lines) {
    final var mapParser = new IntMapParser('.');
    final var antennaParser = new AntennaParser();

    this.map = mapParser.parse(lines);
    this.antennasByFrequency = antennaParser.parse(map);
  }

  public long solve1() {
    return antennasByFrequency.values().stream()
        .flatMap(antennas -> computeAntiNodes(antennas, 1, 1))
        .distinct()
        .count();
  }

  public long solve2() {
    return antennasByFrequency.values().stream()
        .flatMap(antennas -> computeAntiNodes(antennas, 0, Long.MAX_VALUE))
        .distinct()
        .count();
  }

  private Stream<Coordinate> computeAntiNodes(final List<Coordinate> antennas, final long skip, final long limit) {
    return CombinatoricsUtils.objectPairs(antennas)
        .flatMap(pair -> {
          final var diff = pair.right.minus(pair.left);
          return Stream.concat(
              Stream.iterate(pair.left, map::contains, c -> c.minus(diff)).skip(skip).limit(limit),
              Stream.iterate(pair.right, map::contains, c -> c.plus(diff)).skip(skip).limit(limit)
          );
        });
  }
}
