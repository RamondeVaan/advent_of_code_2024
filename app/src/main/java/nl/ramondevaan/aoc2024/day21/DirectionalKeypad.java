package nl.ramondevaan.aoc2024.day21;

import nl.ramondevaan.aoc2024.util.Coordinate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static nl.ramondevaan.aoc2024.day21.Keypad.findRoutes;

public class DirectionalKeypad implements Keypad {
  private final static DirectionalKeypadButton[][][][] PATHS;

  static {
    final var buttons = DirectionalKeypadButton.values();
    final var avoid = Coordinate.of(0, 0);
    final int size = buttons.length;
    PATHS = new DirectionalKeypadButton[size][size][][];
    for (int i = 0; i < size; i++) {
      final var from = buttons[i].coordinate;
      PATHS[i][i] = new DirectionalKeypadButton[][]{{DirectionalKeypadButton.A}};
      for (int j = i + 1; j < size; j++) {
        final var to = buttons[j].coordinate;
        PATHS[i][j] = findRoutes(from, to, avoid).toArray(DirectionalKeypadButton[][]::new);
        PATHS[j][i] = findRoutes(to, from, avoid).toArray(DirectionalKeypadButton[][]::new);
      }
    }
  }

  @Override
  public Stream<List<KeypadButton>> getRobotActions(final int from, final int to) {
    return Arrays.stream(PATHS[from][to]).map(Arrays::asList);
  }
}
