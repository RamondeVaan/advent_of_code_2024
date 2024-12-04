package nl.ramondevaan.aoc2024.day04;

import nl.ramondevaan.aoc2024.util.IntMap;
import nl.ramondevaan.aoc2024.util.Parser;

import java.util.List;

public class WordSearchParser implements Parser<List<String>, IntMap> {
  @Override
  public IntMap parse(List<String> toParse) {
    return new IntMap(toParse.stream().map(String::chars));
  }
}
