package nl.ramondevaan.aoc2024.day12;

import nl.ramondevaan.aoc2024.util.Coordinate;
import nl.ramondevaan.aoc2024.util.Direction;
import nl.ramondevaan.aoc2024.util.IntMap;
import nl.ramondevaan.aoc2024.util.IntMapParser;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.function.ToLongFunction;

public class Day12 {

  private final static List<Direction> DIRECTIONS = Arrays.stream(Direction.values()).toList();

  private final IntMap farm;
  private final IntMap borderedFarm;

  public Day12(final List<String> lines) {
    final var parser = new IntMapParser('A');
    farm = parser.parse(lines);
    borderedFarm = IntMap.builder(farm.rows() + 2, farm.columns() + 2)
        .fill(-1)
        .copyFrom(farm, 0, 1, 0, 1, farm.rows(), farm.columns())
        .build();
  }

  public long solve1() {
    return findRegions(r -> r.area() * r.perimeter());
  }

  public long solve2() {
    return findRegions(r -> r.area() * r.corners());
  }

  private long findRegions(final ToLongFunction<Region> fencePrice) {
    final var visited = new boolean[borderedFarm.rows()][borderedFarm.columns()];
    return Coordinate.streamClosed(1, farm.rows(), 1, farm.columns())
        .filter(plot -> !visited[plot.row()][plot.column()])
        .map(plot -> expandRegion(plot, borderedFarm.valueAt(plot), visited))
        .mapToLong(fencePrice)
        .sum();
  }

  private Region expandRegion(final Coordinate start, final int value, final boolean[][] visited) {
    long area = 0, perimeter = 0, corners = 0;
    visited[start.row()][start.column()] = true;
    final var plots = new ArrayDeque<Coordinate>();
    Coordinate plot = start, neighbor, nextNeighbor;

    do {
      area++;

      for (final var direction : DIRECTIONS) {
        neighbor = direction.apply(plot);
        nextNeighbor = direction.right().apply(plot);
        if (borderedFarm.valueAt(neighbor) != value) {
          perimeter++;
          if (borderedFarm.valueAt(nextNeighbor) != value) {
            corners++;
          }
        } else {
          if (borderedFarm.valueAt(nextNeighbor) == value &&
              borderedFarm.valueAt(direction.apply(nextNeighbor)) != value) {
            corners++;
          }
          if (!visited[neighbor.row()][neighbor.column()]) {
            visited[neighbor.row()][neighbor.column()] = true;
            plots.add(neighbor);
          }
        }
      }
    } while ((plot = plots.pollFirst()) != null);

    return new Region(value, area, perimeter, corners);
  }
}
