package nl.ramondevaan.aoc2024.day14;

import nl.ramondevaan.aoc2024.util.Parser;
import nl.ramondevaan.aoc2024.util.StringIteratorParser;

public class RobotParser implements Parser<String, Robot> {

  private final static char[] POSITION = new char[]{'p', '='};
  private final static char[] VELOCITY = new char[]{' ', 'v', '='};

  @Override
  public Robot parse(final String toParse) {
    final var parser = new StringIteratorParser(toParse);
    parser.consume(POSITION);
    final var px = parser.parseInteger();
    parser.consume(',');
    final var py = parser.parseInteger();
    parser.consume(VELOCITY);
    final var vx = parser.parseInteger();
    parser.consume(',');
    final var vy = parser.parseInteger();

    return new Robot(px, py, vx, vy);
  }
}
