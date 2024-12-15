package nl.ramondevaan.aoc2024.day16;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day16Test {

  static Day16 day16;

  @BeforeAll
  static void setUp() throws URISyntaxException, IOException {
    Path path = Path.of(Objects.requireNonNull(Day16Test.class.getResource("/input/day_16.txt")).toURI());
    List<String> lines = Files.readAllLines(path);
    day16 = new Day16(lines);
  }

  @Test
  void puzzle1() {
    assertEquals(0L, day16.solve1());
  }

  @Test
  void puzzle2() {
    assertEquals(0L, day16.solve2());
  }

}