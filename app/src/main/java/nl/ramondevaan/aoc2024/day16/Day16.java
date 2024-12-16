package nl.ramondevaan.aoc2024.day16;

import nl.ramondevaan.aoc2024.util.Coordinate;
import nl.ramondevaan.aoc2024.util.Direction;

import java.util.*;

import static nl.ramondevaan.aoc2024.day16.Maze.WALL;

public class Day16 {

  private final static List<Direction> DIRECTIONS = Arrays.stream(Direction.values()).toList();
  private final Maze maze;

  public Day16(final List<String> lines) {
    final var parser = new MazeParser();
    this.maze = parser.parse(lines);
  }

  public long solve1() {
    return solve().end.s;
  }

  public long solve2() {
    final var s = solve();

    final var visited = new HashSet<PosDir>();
    final var positions = new HashSet<Coordinate>();
    final var t = new ArrayDeque<PosDir>();
    for (final var k : s.last[s.end.p.row()][s.end.p.column()]) if (k[0] != null && visited.add(k[0])) t.add(k[0]);
    PosDir p;
    while ((p = t.poll()) != null) {
      positions.add(p.p);
      for (final var k : s.last[p.p.row()][p.p.column()][p.d.ordinal()]) if (k != null && visited.add(k)) t.add(k);
    }

    return positions.size() + 1L;
  }

  private Result solve() {
    final var map = this.maze.map();
    final var score = new int[map.rows()][map.columns()][DIRECTIONS.size()];
    Coordinate.stream(0, map.rows(), 0, map.columns())
        .forEach(c -> Arrays.fill(score[c.row()][c.column()], Integer.MAX_VALUE));
    score[0][0][Direction.EAST.ordinal()] = 0;
    final var last = new PosDir[map.rows()][map.columns()][DIRECTIONS.size()][3];

    final var queue = new PriorityQueue<>(Comparator.comparingLong(PosDir::s));
    PosDir current = new PosDir(maze.startPosition(), Direction.EAST, 0, 0);

    do {
      if (current.p.equals(maze.endPosition())) return new Result(current, last);
      for (final var nb : current.neighbors()) {
        if (!map.contains(nb.p) || map.valueAt(nb.p) == WALL || nb.s > score[nb.p.row()][nb.p.column()][nb.d.ordinal()]) {
          continue;
        }
        if (nb.s < score[nb.p.row()][nb.p.column()][nb.d.ordinal()]) {
          Arrays.fill(last[nb.p.row()][nb.p.column()][nb.d.ordinal()], null);
          score[nb.p.row()][nb.p.column()][nb.d.ordinal()] = nb.s;
          queue.add(nb);
        }
        last[nb.p.row()][nb.p.column()][nb.d.ordinal()][nb.id] = current;
      }
    } while ((current = queue.poll()) != null);

    throw new IllegalStateException();
  }

  private record Result(PosDir end, PosDir[][][][] last) {}

  private record PosDir(Coordinate p, Direction d, int s, int id) {

    public PosDir[] neighbors() {
      return new PosDir[]{
          new PosDir(d.apply(p), d, s + 1, 0),
          new PosDir(p, d.left(), s + 1000, 1),
          new PosDir(p, d.right(), s + 1000, 2)
      };
    }
  }
}
