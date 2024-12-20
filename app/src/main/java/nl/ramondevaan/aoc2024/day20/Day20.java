package nl.ramondevaan.aoc2024.day20;

import nl.ramondevaan.aoc2024.util.Coordinate;
import nl.ramondevaan.aoc2024.util.Direction;
import nl.ramondevaan.aoc2024.util.IntMap;
import nl.ramondevaan.aoc2024.util.IntMapEntry;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Day20 {

  private final static List<Direction> DIRECTIONS = Arrays.stream(Direction.values()).toList();
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
    return getCheats(findRoute(), distance).filter(cheat -> cheat.timeSaved >= 100).count();
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

  private Stream<Cheat> getCheats(final Route route, final int distance) {
    return route.path.stream()
        .flatMap(step -> step.coordinate().neighborsWithinDistance(distance)
            .filter(route.map::contains)
            .map(route.map::withValueAt)
            .map(nb -> new Cheat(step, nb, nb.value() - step.value() - step.coordinate().distanceTo(nb.coordinate()))));
  }

  private record Route(IntMap map, List<IntMapEntry> path) {
  }

  private record Cheat(IntMapEntry from, IntMapEntry to, int timeSaved) {
  }
}
