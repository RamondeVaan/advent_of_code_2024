package nl.ramondevaan.aoc2024.day13;

import nl.ramondevaan.aoc2024.util.BlankStringPartitioner;

import java.util.List;

public class Day13 {

  private final static long MULTIPLIER = 10_000_000_000_000L;
  private final List<Machine> machines;

  public Day13(final List<String> lines) {
    final var partitioner = new BlankStringPartitioner();
    final var parser = new MachineParser();

    this.machines = partitioner.partition(lines).stream().map(parser::parse).toList();
  }

  public long solve1() {
    return machines.stream()
        .mapToLong(this::cost)
        .sum();
  }

  public long solve2() {
    return machines.stream()
        .map(m -> new Machine(m.ax(), m.ay(), m.bx(), m.by(), m.px() + MULTIPLIER, m.py() + MULTIPLIER))
        .mapToLong(this::cost)
        .sum();
  }

  private long cost(final Machine m) {
    final var r = m.px() * m.by() - m.bx() * m.py();
    final var s = m.ax() * m.by() - m.bx() * m.ay();

    if (r % s != 0) {
      return 0;
    }

    final var aPresses = r / s;

    final var t = m.py() - aPresses * m.ay();
    if (t % m.by() != 0) {
      return 0;
    }
    final var bPresses = t / m.by();

    return aPresses * 3 + bPresses;
  }
}
