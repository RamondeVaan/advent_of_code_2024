package nl.ramondevaan.aoc2024.day19;

import nl.ramondevaan.aoc2024.util.BlankStringPartitioner;

import java.util.Arrays;
import java.util.List;

public class Day19 {

  private final List<String> designs;
  private final TowelTree tree;

  public Day19(final List<String> lines) {
    final var partitioner = new BlankStringPartitioner();
    final var treeBuilder = TowelTree.builder();

    final var partitions = partitioner.partition(lines);
    Arrays.stream(partitions.getFirst().getFirst().split(", ")).forEach(treeBuilder::add);
    this.tree = treeBuilder.build();
    this.designs = partitions.get(1).stream().toList();
  }

  public long solve1() {
    return designs.stream().filter(d -> countWays(d) > 1).count();
  }

  public long solve2() {
    return designs.stream().mapToLong(this::countWays).sum();
  }

  private long countWays(final String d) {
    final var waysFromIndex = new long[d.length() + 1];
    waysFromIndex[d.length()] = 1;

    for (int from = d.length() - 1; from >= 0; from--)
      tree.towelLengths(d, from, (j, k) -> waysFromIndex[j] += waysFromIndex[k]);

    return waysFromIndex[0];
  }
}
