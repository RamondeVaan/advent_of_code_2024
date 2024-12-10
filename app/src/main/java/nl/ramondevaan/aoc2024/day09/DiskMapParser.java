package nl.ramondevaan.aoc2024.day09;

import com.google.common.primitives.ImmutableIntArray;
import nl.ramondevaan.aoc2024.util.Parser;

public class DiskMapParser implements Parser<String, ImmutableIntArray> {
  @Override
  public ImmutableIntArray parse(final String toParse) {
    final var builder = ImmutableIntArray.builder(toParse.length());

    toParse.chars().map(c -> c - '0').forEach(builder::add);

    return builder.build();
  }
}
