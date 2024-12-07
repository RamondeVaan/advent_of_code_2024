package nl.ramondevaan.aoc2024.util;

import lombok.RequiredArgsConstructor;

import java.io.PrintStream;
import java.util.function.IntFunction;

@RequiredArgsConstructor
public class MapPrinter {

  private final IntFunction<Character> characterFunction;
  private final PrintStream out;

  public void print(final IntMap map) {
    for (int row = 0; row < map.rows(); row++) {
      for (int column = 0; column < map.columns(); column++) {
        final var value = map.valueAt(row, column);
        out.print(characterFunction.apply(value));
      }
      out.printf("%n");
    }
  }
}
