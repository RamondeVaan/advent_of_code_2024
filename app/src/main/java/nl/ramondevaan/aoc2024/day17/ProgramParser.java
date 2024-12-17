package nl.ramondevaan.aoc2024.day17;

import com.google.common.primitives.ImmutableIntArray;
import nl.ramondevaan.aoc2024.util.Parser;
import nl.ramondevaan.aoc2024.util.StringIteratorParser;

public class ProgramParser implements Parser<String, ImmutableIntArray> {

  private final static char[] PROGRAM = new char[]{'P', 'r', 'o', 'g', 'r', 'a', 'm', ':', ' '};

  @Override
  public ImmutableIntArray parse(final String toParse) {
    final var builder = ImmutableIntArray.builder();
    final var parser = new StringIteratorParser(toParse);
    parser.consume(PROGRAM);
    builder.add(parser.parseInteger());

    while (parser.hasNext()) {
      parser.consume(',');
      builder.add(parser.parseInteger());
    }

    return builder.build();
  }
}
