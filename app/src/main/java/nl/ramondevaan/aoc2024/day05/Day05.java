package nl.ramondevaan.aoc2024.day05;

import com.google.common.primitives.ImmutableIntArray;
import nl.ramondevaan.aoc2024.util.BlankStringPartitioner;

import java.util.Comparator;
import java.util.List;

public class Day05 {

  private final List<ImmutableIntArray> updates;
  private final Comparator<Integer> orderingRuleComparator;

  public Day05(final List<String> lines) {
    final var partitioner = new BlankStringPartitioner();
    final var orderingRuleComparatorParser = new OrderingRuleComparatorParser();
    final var pagesParser = new UpdateParser();

    final var partitions = partitioner.partition(lines);
    this.orderingRuleComparator = orderingRuleComparatorParser.parse(partitions.getFirst());
    this.updates = partitions.get(1).stream().map(pagesParser::parse).toList();
  }

  public long solve1() {
    return updates.stream()
        .filter(update -> update.equals(sort(update)))
        .mapToLong(Day05::middle)
        .sum();
  }

  public long solve2() {
    return updates.stream()
        .mapToLong(update -> {
          final var sorted = sort(update);
          if (update.equals(sorted)) {
            return 0L;
          }
          return middle(sorted);
        })
        .sum();
  }

  private ImmutableIntArray sort(final ImmutableIntArray update) {
    return ImmutableIntArray.copyOf(update.stream().boxed().sorted(orderingRuleComparator).mapToInt(i -> i));
  }

  private static int middle(final ImmutableIntArray update) {
    return update.get(update.length() / 2);
  }
}
