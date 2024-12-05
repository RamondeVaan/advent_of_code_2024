package nl.ramondevaan.aoc2024.day05;

import nl.ramondevaan.aoc2024.util.Pair;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class OrderingRuleComparator implements Comparator<Integer> {

  private final Map<Integer, Set<Integer>> orderingRules;

  public OrderingRuleComparator(final Stream<Pair> orderingRules) {
    this.orderingRules = orderingRules.collect(groupingBy(Pair::left, mapping(Pair::right, toUnmodifiableSet())));
  }

  @Override
  public int compare(final Integer o1, final Integer o2) {
    if (orderingRules.getOrDefault(o1, Collections.emptySet()).contains(o2)) {
      return -1;
    }
    if (orderingRules.getOrDefault(o2, Collections.emptySet()).contains(o1)) {
      return 1;
    }
    return 0;
  }
}
