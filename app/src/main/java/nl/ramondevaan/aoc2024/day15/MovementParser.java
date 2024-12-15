package nl.ramondevaan.aoc2024.day15;

import nl.ramondevaan.aoc2024.util.Direction;
import nl.ramondevaan.aoc2024.util.Parser;

import java.util.List;

public class MovementParser implements Parser<List<String>, List<Direction>> {
  @Override
  public List<Direction> parse(final List<String> toParse) {
    return toParse.stream().flatMapToInt(String::chars).mapToObj(this::parseDirection).toList();
  }

  private Direction parseDirection(final int character) {
    return switch (character) {
      case '^' -> Direction.NORTH;
      case '>' -> Direction.EAST;
      case 'v' -> Direction.SOUTH;
      case '<' -> Direction.WEST;
      default -> throw new IllegalStateException("Unexpected value: " + character);
    };
  }
}
