package nl.ramondevaan.aoc2024.day21;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.ramondevaan.aoc2024.util.Coordinate;

@RequiredArgsConstructor
@Getter
public enum DirectionalKeypadButton implements KeypadButton {
  UP(new Coordinate(0, 1), -1, 0),
  LEFT(new Coordinate(1, 0), 0, -1),
  DOWN(new Coordinate(1, 1), 1, 0),
  RIGHT(new Coordinate(1, 2), 0, 1),
  A(new Coordinate(0, 2), 0, 0);

  public final Coordinate coordinate;
  public final int rowDiff;
  public final int columnDiff;

  public Coordinate apply(final Coordinate coordinate) {
    return Coordinate.of(coordinate.row() + rowDiff, coordinate.column() + columnDiff);
  }
}
