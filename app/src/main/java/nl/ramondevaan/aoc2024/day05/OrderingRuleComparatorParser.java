package nl.ramondevaan.aoc2024.day05;

import nl.ramondevaan.aoc2024.util.Pair;
import nl.ramondevaan.aoc2024.util.Parser;
import nl.ramondevaan.aoc2024.util.StringIteratorParser;

import java.util.List;

public class OrderingRuleComparatorParser implements Parser<List<String>, OrderingRuleComparator> {
  @Override
  public OrderingRuleComparator parse(final List<String> toParse) {
    return new OrderingRuleComparator(toParse.stream().map(this::parse));
  }

  private Pair parse(final String toParse) {
    final var parser = new StringIteratorParser(toParse);
    final var left = parser.parseInteger();
    parser.consume('|');
    final var right = parser.parseInteger();

    return new Pair(left, right);
  }
}
