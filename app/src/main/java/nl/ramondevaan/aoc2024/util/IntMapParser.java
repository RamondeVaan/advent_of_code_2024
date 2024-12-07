package nl.ramondevaan.aoc2024.util;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class IntMapParser implements Parser<List<String>, IntMap> {

  private final char subtract;

  public IntMapParser() {
    this('0');
  }

  @Override
  public IntMap parse(List<String> toParse) {
    return new IntMap(toParse.stream().map(line -> line.chars().map(character -> character - '0')));
  }
}
