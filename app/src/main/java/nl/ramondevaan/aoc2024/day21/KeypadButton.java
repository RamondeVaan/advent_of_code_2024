package nl.ramondevaan.aoc2024.day21;

import nl.ramondevaan.aoc2024.util.Coordinate;

public interface KeypadButton {

  Coordinate getCoordinate();

  int ordinal();
}
