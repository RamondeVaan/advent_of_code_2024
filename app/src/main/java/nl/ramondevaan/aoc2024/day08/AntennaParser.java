package nl.ramondevaan.aoc2024.day08;

import nl.ramondevaan.aoc2024.util.Coordinate;
import nl.ramondevaan.aoc2024.util.IntMap;
import nl.ramondevaan.aoc2024.util.IntMapEntry;
import nl.ramondevaan.aoc2024.util.Parser;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

public class AntennaParser implements Parser<IntMap, Map<Integer, List<Coordinate>>> {
  @Override
  public Map<Integer, List<Coordinate>> parse(final IntMap toParse) {
    return toParse.streamEntries()
        .filter(entry -> entry.value() != 0)
        .collect(groupingBy(IntMapEntry::value, mapping(IntMapEntry::coordinate, toUnmodifiableList())));
  }
}
