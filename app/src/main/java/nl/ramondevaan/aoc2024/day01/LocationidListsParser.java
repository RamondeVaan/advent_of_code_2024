package nl.ramondevaan.aoc2024.day01;

import com.google.common.primitives.ImmutableIntArray;
import nl.ramondevaan.aoc2024.util.Parser;
import nl.ramondevaan.aoc2024.util.StringIteratorParser;

import java.util.List;

public class LocationidListsParser implements Parser<List<String>, LocationIdLists> {

    private final static char[] SEPARATOR = new char[] {' ', ' ', ' '};

    @Override
    public LocationIdLists parse(final List<String> toParse) {
        final var left = ImmutableIntArray.builder(toParse.size());
        final var right = ImmutableIntArray.builder(toParse.size());

        for (final var line : toParse) {
            final var parser = new StringIteratorParser(line);
            left.add(parser.parseInteger());
            parser.consume(SEPARATOR);
            right.add(parser.parseInteger());
            parser.verifyIsDone();
        }

        return new LocationIdLists(left.build(), right.build());
    }
}
