package nl.ramondevaan.aoc2024.day17;

import com.google.common.primitives.ImmutableIntArray;
import nl.ramondevaan.aoc2024.util.BlankStringPartitioner;

import java.util.List;
import java.util.OptionalLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Day17 {

  private final ImmutableIntArray registers;
  private final ImmutableIntArray program;

  public Day17(final List<String> lines) {
    final var registersParser = new RegistersParser();
    final var programParser = new ProgramParser();
    final var partitioner = new BlankStringPartitioner();

    final var partitions = partitioner.partition(lines);
    this.registers = registersParser.parse(partitions.getFirst());
    this.program = programParser.parse(partitions.get(1).getFirst());
  }

  public String solve1() {
    return getOutput(registers.get(0), registers.get(1), registers.get(2))
        .stream().mapToObj(String::valueOf).collect(Collectors.joining(","));
  }

  public long solve2() {
    return findA(0, program.length() - 1).orElseThrow();
  }

  private OptionalLong findA(final long current, final int checkFrom) {
    return IntStream.range(0, 8)
        .mapToLong(i -> current << 3 | i)
        .filter(a -> program.subArray(checkFrom, program.length()).equals(getOutput(a, 0, 0)))
        .flatMap(a -> checkFrom == 0 ? LongStream.of(a) : findA(a, checkFrom - 1).stream())
        .min();
  }

  private ImmutableIntArray getOutput(long a, long b, long c) {
    var i = 0;
    final var builder = ImmutableIntArray.builder();
    while (i < program.length()) {
      switch (program.get(i)) {
        case 0 -> a = a >> getCombo(a, b, c, i);
        case 1 -> b ^= program.get(i + 1);
        case 2 -> b = (getCombo(a, b, c, i) & 0b111);
        case 3 -> {
          if (a != 0) i = program.get(i + 1) - 2;
        }
        case 4 -> b ^= c;
        case 5 -> builder.add((int) (getCombo(a, b, c, i) & 0b111));
        case 6 -> b = a >> getCombo(a, b, c, i);
        case 7 -> c = a >> getCombo(a, b, c, i);
      }
      i += 2;
    }

    return builder.build();
  }

  private long getCombo(final long a, final long b, final long c, final int i) {
    final var v = program.get(i + 1);
    return switch (v) {
      case 4 -> a;
      case 5 -> b;
      case 6 -> c;
      case 7 -> throw new IllegalStateException("Reserved");
      default -> v;
    };
  }
}
