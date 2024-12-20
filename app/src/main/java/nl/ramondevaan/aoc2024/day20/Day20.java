package nl.ramondevaan.aoc2024.day20;

import nl.ramondevaan.aoc2024.util.Coordinate;
import nl.ramondevaan.aoc2024.util.IntMap;
import nl.ramondevaan.aoc2024.util.IntMapEntry;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class Day20 {

  private static final int MIN = 100;
  private final RaceTrack raceTrack;

  public Day20(final List<String> lines) {
    final var parser = new RaceTrackParser();
    this.raceTrack = parser.parse(lines);
  }

  public long solve1() {
    return solve(2);
  }

  public long solve2() {
    return solve(20);
  }

  private long solve(final int distance) {
    return getCheats(findRoute(), distance);
  }

  private Route findRoute() {
    final var path = new ArrayList<IntMapEntry>();
    final var builder = raceTrack.map().toBuilder();
    builder.set(raceTrack.start(), 1);

    final var queue = new ArrayDeque<Coordinate>();
    var current = raceTrack.start();

    do {
      final var entry = builder.withValueAt(current);
      path.add(entry);
      final var nextValue = builder.get(current) + 1;

      current.directNeighbors()
          .filter(builder::contains)
          .filter(nb -> builder.get(nb) == 0)
          .peek(queue::add)
          .forEach(nb -> builder.set(nb, nextValue));
    } while ((current = queue.pollFirst()) != null);

    return new Route(builder.build(), path);
  }

  private long getCheats(final Route r, final int dist) {
    var sum = 0;
    for (final var s : r.path)
      for (final var nb : s.coordinate().neighborsWithinDistance(dist))
        if (r.map.contains(nb) && r.map.valueAt(nb) - s.value() - s.coordinate().distanceTo(nb) >= MIN) sum++;
    return sum;
  }

  private record Route(IntMap map, List<IntMapEntry> path) {
  }
}
