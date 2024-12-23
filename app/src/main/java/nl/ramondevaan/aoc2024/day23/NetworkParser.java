package nl.ramondevaan.aoc2024.day23;

import nl.ramondevaan.aoc2024.util.Parser;
import nl.ramondevaan.aoc2024.util.StringIteratorParser;

import java.util.List;

public class NetworkParser implements Parser<List<String>, Network> {
  @Override
  public Network parse(final List<String> toParse) {
    final var network = new Network();

    for (final var line : toParse) {
      final var parser = new StringIteratorParser(line);
      final var from = parseVertex(parser);
      parser.consume('-');
      final var to = parseVertex(parser);
      parser.verifyIsDone();
      network.add(from, to);
    }

    return network;
  }

  private int parseVertex(final StringIteratorParser parser) {
    var ret = (parser.current() - 'a') * 26;
    parser.consume();
    ret += (parser.current() - 'a');
    parser.consume();
    return ret;
  }
}
