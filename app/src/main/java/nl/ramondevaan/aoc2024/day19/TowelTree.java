package nl.ramondevaan.aoc2024.day19;

import nl.ramondevaan.aoc2024.util.IntBiConsumer;

public class TowelTree {
  private final TowelTree[] map = new TowelTree[123];
  private boolean exists = false;

  public void towelLengths(final String s, final int from, final IntBiConsumer consumer) {
    final var chars = s.toCharArray();
    int index = from;
    TowelTree current = this;
    do {
      if (current.exists) consumer.consume(from, index);
    } while (index < chars.length && (current = current.map[chars[index++]]) != null);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private TowelTree tree = new TowelTree();

    public Builder add(final String s) {
      TowelTree current = tree;
      for (final char c : s.toCharArray()) {
        if (current.map[c] == null) current.map[c] = new TowelTree();
        current = current.map[c];
      }
      current.exists = true;
      return this;
    }

    public TowelTree build() {
      final var ret = tree;
      this.tree = null;
      return ret;
    }
  }
}
