package nl.ramondevaan.aoc2024.day01;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class Day01Test {

    static Day01 day01;

    @BeforeAll
    static void setUp() throws URISyntaxException, IOException {
        Path path = Path.of(Objects.requireNonNull(Day01Test.class.getResource("/input/day_01.txt")).toURI());
        List<String> lines = Files.readAllLines(path);
        day01 = new Day01(lines);
    }

    @Test
    void puzzle1() {
        assertEquals(6L, day01.solve1());
    }

    @Test
    void puzzle2() {
        assertEquals(12L, day01.solve2());
    }

}