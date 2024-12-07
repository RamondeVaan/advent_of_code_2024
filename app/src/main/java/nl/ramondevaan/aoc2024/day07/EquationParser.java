package nl.ramondevaan.aoc2024.day07;

import com.google.common.primitives.ImmutableLongArray;
import nl.ramondevaan.aoc2024.util.Parser;
import nl.ramondevaan.aoc2024.util.StringIteratorParser;

public class EquationParser implements Parser<String, Equation> {


  @Override
  public Equation parse(final String toParse) {
    final var parser = new StringIteratorParser(toParse);

    final var testValue = parser.parseLong();
    parser.consume(':');

    final var builder = ImmutableLongArray.builder();

    do {
      parser.consume(' ');
      builder.add(parser.parseLong());
    } while (parser.hasNext());

    return new Equation(testValue, builder.build());
  }
}
