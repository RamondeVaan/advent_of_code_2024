package nl.ramondevaan.aoc2024.util;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class FileIntMapParser implements Parser<URL, IntMap> {

  private final IntMapParser parser;

  public FileIntMapParser(final char subtract) {
    this.parser = new IntMapParser(subtract);
  }

  @Override
  public IntMap parse(final URL url) {
    final var lines = getLines(url).toList();
    return parser.parse(lines);
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
