package nl.ramondevaan.aoc2024.day13;

import nl.ramondevaan.aoc2024.util.Parser;
import nl.ramondevaan.aoc2024.util.StringIteratorParser;

import java.util.List;

public class MachineParser implements Parser<List<String>, Machine> {

  private static final char[] BUTTON = new char[]{'B', 'u', 't', 't', 'o', 'n', ' '};
  private static final char[] PRIZE = new char[]{'P', 'r', 'i', 'z', 'e'};
  private static final char[] X_ADD = new char[]{'X', '+'};
  private static final char[] Y_ADD = new char[]{'Y', '+'};
  private static final char[] X_DEF = new char[]{'X', '='};
  private static final char[] Y_DEF = new char[]{'Y', '='};
  private static final char[] LABEL_SEPARATOR = new char[]{':', ' '};
  private static final char[] COORDINATE_SEPARATOR = new char[]{',', ' '};

  @Override
  public Machine parse(final List<String> toParse) {
    if (toParse.size() != 3) {
      throw new IllegalArgumentException();
    }
    final var aButton = parseButton('A', toParse.getFirst());
    final var bButton = parseButton('B', toParse.get(1));
    final var prize = parsePrize(toParse.get(2));

    return new Machine(aButton.x, aButton.y, bButton.x, bButton.y, prize.x, prize.y);
  }

  private Pair parseButton(final char buttonLabel, final String toParse) {
    final var parser = new StringIteratorParser(toParse);
    parser.consume(BUTTON);
    parser.consume(buttonLabel);
    parser.consume(LABEL_SEPARATOR);
    parser.consume(X_ADD);
    final var x = parser.parseLong();
    parser.consume(COORDINATE_SEPARATOR);
    parser.consume(Y_ADD);
    final var y = parser.parseLong();

    return new Pair(x, y);
  }

  private Pair parsePrize(final String toParse) {
    final var parser = new StringIteratorParser(toParse);
    parser.consume(PRIZE);
    parser.consume(LABEL_SEPARATOR);
    parser.consume(X_DEF);
    final var x = parser.parseLong();
    parser.consume(COORDINATE_SEPARATOR);
    parser.consume(Y_DEF);
    final var y = parser.parseLong();

    return new Pair(x, y);
  }

  private record Pair(long x, long y) {
  }
}
