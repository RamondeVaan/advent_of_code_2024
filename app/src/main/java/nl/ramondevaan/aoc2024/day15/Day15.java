package nl.ramondevaan.aoc2024.day15;

import nl.ramondevaan.aoc2024.util.*;

import java.util.HashSet;
import java.util.List;

import static java.util.stream.IntStream.range;
import static nl.ramondevaan.aoc2024.day15.Warehouse.*;

public class Day15 {

  private final Warehouse warehouse;
  private final Warehouse wideWarehouse;
  private final List<Direction> movements;

  public Day15(final List<String> lines) {
    final var partitioner = new BlankStringPartitioner();
    final var warehouseParser = new WarehouseParser();
    final var wideWarehouseParser = new WideWarehouseParser();
    final var movementParser = new MovementParser();

    final var partitions = partitioner.partition(lines);
    this.warehouse = warehouseParser.parse(partitions.getFirst());
    this.wideWarehouse = wideWarehouseParser.parse(warehouse);
    this.movements = movementParser.parse(partitions.getLast());
  }

  public long solve1() {
    return solve(warehouse, BOX);
  }

  public long solve2() {
    return solve(wideWarehouse, BOX_LEFT);
  }

  private long solve(final Warehouse warehouse, final int gpsType) {
    final var map = warehouse.map().toBuilder();
    Coordinate robot = warehouse.robot();

    for (final var movement : movements) {
      if (move(map, List.of(robot), movement)) {
        robot = movement.apply(robot);
      }
    }

    return map.build()
        .streamEntries()
        .filter(entry -> entry.value() == gpsType)
        .map(IntMapEntry::coordinate)
        .mapToLong(box -> box.row() * 100L + box.column())
        .sum();
  }

  private static boolean move(final IntMap.Builder builder, final List<Coordinate> positions,
      final Direction direction) {
    final var positionsPlusOne = positions.stream().map(direction::apply).toList();
    final var pushingPositions = new HashSet<Coordinate>();
    for (final var coordinate : positionsPlusOne) {
      var value = builder.get(coordinate);
      if (value == EMPTY) continue;
      if (value == WALL) return false;
      pushingPositions.add(coordinate);
      if ((value *= direction.absRow) == BOX_LEFT) pushingPositions.add(Direction.EAST.apply(coordinate));
      else if (value == BOX_RIGHT) pushingPositions.add(Direction.WEST.apply(coordinate));
    }

    if (pushingPositions.isEmpty() || move(builder, List.copyOf(pushingPositions), direction)) {
      pushingPositions.forEach(c -> builder.set(c, 0));
      range(0, positions.size()).forEach(i -> builder.set(positionsPlusOne.get(i), builder.get(positions.get(i))));
      return true;
    }
    return false;
  }
}
