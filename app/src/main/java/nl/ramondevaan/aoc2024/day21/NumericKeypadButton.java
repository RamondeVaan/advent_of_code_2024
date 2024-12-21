package nl.ramondevaan.aoc2024.day21;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.ramondevaan.aoc2024.util.Coordinate;

@RequiredArgsConstructor
@Getter
public enum NumericKeypadButton implements KeypadButton {
  _0(new Coordinate(3, 1)),
  _1(new Coordinate(2, 0)),
  _2(new Coordinate(2, 1)),
  _3(new Coordinate(2, 2)),
  _4(new Coordinate(1, 0)),
  _5(new Coordinate(1, 1)),
  _6(new Coordinate(1, 2)),
  _7(new Coordinate(0, 0)),
  _8(new Coordinate(0, 1)),
  _9(new Coordinate(0, 2)),
  A(new Coordinate(3, 2));

  public final Coordinate coordinate;
}
