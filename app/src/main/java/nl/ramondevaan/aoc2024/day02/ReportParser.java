package nl.ramondevaan.aoc2024.day02;

import com.google.common.primitives.ImmutableIntArray;
import nl.ramondevaan.aoc2024.util.Parser;
import nl.ramondevaan.aoc2024.util.StringIteratorParser;

public class ReportParser implements Parser<String, ImmutableIntArray> {
  @Override
  public ImmutableIntArray parse(final String toParse) {
    final var parser = new StringIteratorParser(toParse);
    final var levelsBuilder = ImmutableIntArray.builder();

    levelsBuilder.add(parser.parseInteger());
    while (parser.hasNext()) {
      parser.consume(' ');
      levelsBuilder.add(parser.parseInteger());
    }

    return levelsBuilder.build();
  }
}
