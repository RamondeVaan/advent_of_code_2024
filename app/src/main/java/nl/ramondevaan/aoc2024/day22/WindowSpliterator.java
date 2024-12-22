package nl.ramondevaan.aoc2024.day22;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.LongUnaryOperator;

public class WindowSpliterator implements Spliterator<WindowSpliterator.PriceChanges> {

  private final LongUnaryOperator nextFunction;
  private final int mask;
  private int changes;
  private long last;
  private int lastBananas;

  public WindowSpliterator(final long secret, final int windowSize, LongUnaryOperator nextFunction) {
    this.nextFunction = nextFunction;
    this.last = secret;
    this.lastBananas = (int) secret % 10;
    this.mask = (1 << (5 * windowSize)) - 1;
  }

  @Override
  public boolean tryAdvance(final Consumer<? super PriceChanges> action) {
    long next = nextFunction.applyAsLong(last);
    int nextBananas = (int) next % 10;
    changes = ((changes << 5) & mask) + (nextBananas - lastBananas + 9);
    action.accept(new PriceChanges(changes, nextBananas));
    last = next;
    lastBananas = nextBananas;
    return true;
  }

  @Override
  public Spliterator<PriceChanges> trySplit() {
    return null;
  }

  @Override
  public long estimateSize() {
    return Long.MAX_VALUE;
  }

  @Override
  public int characteristics() {
    return ORDERED | IMMUTABLE | NONNULL;
  }

  public record PriceChanges(int changes, long price) {
    @Override
    public boolean equals(final Object o) {
      if (o == null || getClass() != o.getClass()) return false;
      final PriceChanges price = (PriceChanges) o;
      return changes == price.changes;
    }

    @Override
    public int hashCode() {
      return changes;
    }
  }
}
