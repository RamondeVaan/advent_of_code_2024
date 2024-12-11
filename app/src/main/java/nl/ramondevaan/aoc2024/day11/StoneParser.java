package nl.ramondevaan.aoc2024.day11;

import com.google.common.primitives.ImmutableLongArray;
import nl.ramondevaan.aoc2024.util.Parser;
import nl.ramondevaan.aoc2024.util.StringIteratorParser;

public class StoneParser implements Parser<String, ImmutableLongArray> {
  @Override
  public ImmutableLongArray parse(final String toParse) {
    final var parser = new StringIteratorParser(toParse);
    final var builder = ImmutableLongArray.builder();

    builder.add(parser.parseInteger());
    while (parser.hasNext()) {
      parser.consume(' ');
      builder.add(parser.parseInteger());
    }
    ;

    return builder.build();
  }
}
