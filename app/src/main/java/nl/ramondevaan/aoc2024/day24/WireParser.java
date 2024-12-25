package nl.ramondevaan.aoc2024.day24;

import nl.ramondevaan.aoc2024.util.Parser;
import nl.ramondevaan.aoc2024.util.StringIteratorParser;

public class WireParser implements Parser<String, Wire> {

  private final static char[] SEPARATOR = new char[]{':', ' '};

  @Override
  public Wire parse(final String toParse) {
    final var parser = new StringIteratorParser(toParse);
    final var id = parser.parseAlphaNumeric();
    parser.consume(SEPARATOR);
    final var value = parseBoolean(parser);
    parser.verifyIsDone();

    return new Wire(id, value);
  }

  private boolean parseBoolean(final StringIteratorParser parser) {
    final var c = parser.current();
    parser.consume();
    return switch (c) {
      case '0' -> false;
      case '1' -> true;
      default -> throw new IllegalStateException("Unexpected value: " + parser.current());
    };
  }
}
