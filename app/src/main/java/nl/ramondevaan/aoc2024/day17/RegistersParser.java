package nl.ramondevaan.aoc2024.day17;

import com.google.common.primitives.ImmutableIntArray;
import nl.ramondevaan.aoc2024.util.Parser;
import nl.ramondevaan.aoc2024.util.StringIteratorParser;

import java.util.List;

public class RegistersParser implements Parser<List<String>, ImmutableIntArray> {

  private final static char[] REGISTER = new char[] {'R', 'e', 'g', 'i', 's', 't', 'e', 'r', ' '};
  private final static char[] SEPARATOR = new char[] {':', ' '};

  @Override
  public ImmutableIntArray parse(final List<String> toParse) {
    if (toParse.size() != 3) {
      throw new IllegalArgumentException();
    }
    final var builder = ImmutableIntArray.builder();

    builder.add(parse(toParse.getFirst(), 'A'));
    builder.add(parse(toParse.get(1), 'B'));
    builder.add(parse(toParse.get(2), 'C'));

    return builder.build();
  }

  private int parse(final String toParse, final char register) {
    final var parser = new StringIteratorParser(toParse);
    parser.consume(REGISTER);
    parser.consume(register);
    parser.consume(SEPARATOR);
    return parser.parseInteger();
  }
}
