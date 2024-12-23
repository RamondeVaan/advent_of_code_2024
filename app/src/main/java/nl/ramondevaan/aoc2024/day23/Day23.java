package nl.ramondevaan.aoc2024.day23;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day23 {

  private final Network network;

  public Day23(final List<String> lines) {
    final var parser2 = new NetworkParser();
    this.network = parser2.parse(lines);
  }

  public long solve1() {
    var sum = 0L;
    int nb1, nb2;
    for (int i = 494; i < 520; i++) {
      for (int j = 0; j < network.neighborsSize[i]; j++) {
        if ((nb1 = network.neighbors[i][j]) >= 494 && nb1 <= i) continue;
        for (int k = j + 1; k < network.neighborsSize[i]; k++) {
          if ((nb2 = network.neighbors[i][k]) >= 494 && nb2 <= i) continue;
          if (network.connected[nb1][nb2]) sum++;
        }
      }
    }
    return sum;
  }

  public String solve2() {
    final var maxCliqueFinder = new MaxCliqueFinder();
    final var max = maxCliqueFinder.compute(network);

    return Arrays.stream(max).sorted()
        .mapToObj(i -> String.valueOf(new char[] {(char) (i / 26 + 'a'), (char) (i % 26 + 'a')}))
        .collect(Collectors.joining(","));
  }
}
