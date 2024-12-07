package nl.ramondevaan.aoc2024.day06;

import nl.ramondevaan.aoc2024.util.Coordinate;
import nl.ramondevaan.aoc2024.util.Direction;
import nl.ramondevaan.aoc2024.util.IntMap;
import nl.ramondevaan.aoc2024.util.IntMapEntry;

import java.util.List;

public class Day06 {

  private final static int OBSTRUCTION = -1;
  private final Map map;

  public Day06(final List<String> lines) {
    final var parser = new MapParser(OBSTRUCTION);
    this.map = parser.parse(lines);
  }

  public long solve1() {
    return doPatrol().values().filter(i -> i > 0).count() + 1L;
  }

  public long solve2() {
    final var potentialObstacleCoordinates = doPatrol().streamEntries()
        .filter(i -> i.value() > 0)
        .map(IntMapEntry::coordinate)
        .toList();

    return potentialObstacleCoordinates.stream()
        .map(coordinate -> map.map().toBuilder().set(coordinate, OBSTRUCTION))
        .filter(mapWithNewObstacle -> doPatrol(mapWithNewObstacle, map.guardPosition(), map.guardDirection()))
        .count();
  }

  private IntMap doPatrol() {
    final var mutableMap = map.map().toBuilder();
    doPatrol(mutableMap, map.guardPosition(), map.guardDirection());
    mutableMap.set(map.guardPosition(), 0);
    return mutableMap.build();
  }

  private static boolean doPatrol(final IntMap.Builder mutableMap, final Coordinate startPosition, final Direction startDirection) {
    var position = startPosition;
    var direction = startDirection;

    do {
      if (mutableMap.hasFlag(position, direction.getFlag())) {
        return true; // Loop detected
      }
      mutableMap.flag(position, direction.getFlag());
      var nextPosition = direction.apply(position);
      if (!mutableMap.contains(nextPosition)) {
        break;
      }
      if (mutableMap.get(nextPosition) == OBSTRUCTION) {
        direction = direction.right();
      } else {
        position = nextPosition;
      }
    } while (mutableMap.contains(position));

    return false; // No loop
  }
}
