package nl.ramondevaan.aoc2024.day06;

import nl.ramondevaan.aoc2024.util.Coordinate;
import nl.ramondevaan.aoc2024.util.Direction;
import nl.ramondevaan.aoc2024.util.IntMap;

public record Map(IntMap map, Coordinate guardPosition, Direction guardDirection) {
}
