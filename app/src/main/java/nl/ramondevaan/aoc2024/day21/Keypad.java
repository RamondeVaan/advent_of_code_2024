package nl.ramondevaan.aoc2024.day21;

import com.google.common.collect.HashMultiset;
import nl.ramondevaan.aoc2024.util.CombinatoricsUtils;
import nl.ramondevaan.aoc2024.util.Coordinate;

import java.util.List;
import java.util.stream.Stream;

public interface Keypad {

  static Stream<DirectionalKeypadButton[]> findRoutes(final Coordinate from, final Coordinate to, final Coordinate avoid) {
    final var ret = HashMultiset.<DirectionalKeypadButton>create();
    for (int i = 0; i < to.column() - from.column(); i++) ret.add(DirectionalKeypadButton.RIGHT);
    for (int i = 0; i < from.column() - to.column(); i++) ret.add(DirectionalKeypadButton.LEFT);
    for (int i = 0; i < to.row() - from.row(); i++) ret.add(DirectionalKeypadButton.DOWN);
    for (int i = 0; i < from.row() - to.row(); i++) ret.add(DirectionalKeypadButton.UP);
    return CombinatoricsUtils.multisetPermutations(ret, DirectionalKeypadButton[]::new)
        .filter(actions -> {
          if (from.equals(avoid)) return false;
          var coordinate = from;
          for (final var action : actions) if ((coordinate = action.apply(coordinate)).equals(avoid)) return false;
          return true;
        })
        .map(arr -> {
          final var t = new DirectionalKeypadButton[arr.length + 1];
          System.arraycopy(arr, 0, t, 0, arr.length);
          t[arr.length] = DirectionalKeypadButton.A;
          return t;
        });
  }

  Stream<List<KeypadButton>> getRobotActions(int from, int to);
}
