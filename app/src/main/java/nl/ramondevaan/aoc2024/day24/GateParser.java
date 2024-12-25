package nl.ramondevaan.aoc2024.day24;

import nl.ramondevaan.aoc2024.util.Parser;
import nl.ramondevaan.aoc2024.util.StringIteratorParser;

public class GateParser implements Parser<String, Gate> {

  private final static char[] SEPARATOR = new char[]{' ', '-', '>', ' '};

  @Override
  public Gate parse(final String toParse) {
    final var parser = new StringIteratorParser(toParse);
    final var leftId = parser.parseAlphaNumeric();
    parser.consume(' ');
    final var operator = switch (parser.parseString()) {
      case "AND" -> Operator.AND;
      case "OR" -> Operator.OR;
      case "XOR" -> Operator.XOR;
      default -> throw new IllegalStateException("Unexpected value: " + parser.parseString());
    };
    parser.consume(' ');
    final var rightId = parser.parseAlphaNumeric();
    parser.consume(SEPARATOR);
    final var resultId = parser.parseAlphaNumeric();
    return new Gate(leftId, rightId, resultId, operator);
  }
}
