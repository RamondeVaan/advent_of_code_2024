package nl.ramondevaan.aoc2024.util;

public class CoordinateParser implements Parser<String, Coordinate> {
  @Override
  public Coordinate parse(final String toParse) {
    final var parser = new StringIteratorParser(toParse);
    final var row = parser.parseInteger();
    parser.consume(',');
    final var column = parser.parseInteger();
    return new Coordinate(row, column);
  }
}
