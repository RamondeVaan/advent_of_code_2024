package nl.ramondevaan.aoc2024.day04;

import lombok.RequiredArgsConstructor;
import nl.ramondevaan.aoc2024.util.BlankStringPartitioner;
import nl.ramondevaan.aoc2024.util.IntMap;
import nl.ramondevaan.aoc2024.util.Parser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class PatternParser implements Parser<URL, List<IntMap>> {

  private final BlankStringPartitioner partitioner;
  private final WordSearchParser wordSearchParser;

  @Override
  public List<IntMap> parse(final URL url) {
    final var lines = getLines(url).toList();
    return partitioner.partition(lines).stream()
        .map(wordSearchParser::parse)
        .toList();
  }

  private Stream<String> getLines(URL url) {
    try {
      final var path = Path.of(Objects.requireNonNull(url).toURI());
      return Files.lines(path);
    } catch (URISyntaxException | IOException e) {
      throw new RuntimeException(e);
    }
  }
}
