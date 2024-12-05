package nl.ramondevaan.aoc2024.day05;

import com.google.common.primitives.ImmutableIntArray;
import nl.ramondevaan.aoc2024.util.Parser;
import nl.ramondevaan.aoc2024.util.StringIteratorParser;

public class UpdateParser implements Parser<String, ImmutableIntArray> {
  @Override
  public ImmutableIntArray parse(final String toParse) {
    final var builder = ImmutableIntArray.builder();
    final var parser = new StringIteratorParser(toParse);

    do {
      builder.add(parser.parseInteger());
    } while (parser.consumeIfPresent(','));

    return builder.build();
  }
}
