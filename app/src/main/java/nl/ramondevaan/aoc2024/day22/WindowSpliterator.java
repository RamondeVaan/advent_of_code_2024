package nl.ramondevaan.aoc2024.day22;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.LongUnaryOperator;
import java.util.stream.Stream;

public class WindowSpliterator implements Spliterator<WindowSpliterator.Price> {

  private final ArrayDeque<Long> window;
  private final LongUnaryOperator nextFunction;
  private long last;
  private long lastBananas;

  public WindowSpliterator(final long secret, final int dequeSize, LongUnaryOperator nextFunction) {
    this.nextFunction = nextFunction;
    this.last = secret;
    this.lastBananas = secret % 10;
    this.window = new ArrayDeque<>(dequeSize);
    window.addAll(Stream.generate(() -> Long.MAX_VALUE).limit(dequeSize).toList());
  }

  @Override
  public boolean tryAdvance(final Consumer<? super WindowSpliterator.Price> action) {
    long next = nextFunction.applyAsLong(last);
    long nextBananas = next % 10;
    window.pollFirst();
    window.addLast(nextBananas - lastBananas);
    action.accept(new Price(window.stream().toList(), nextBananas));
    last = next;
    lastBananas = nextBananas;
    return true;
  }

  @Override
  public Spliterator<WindowSpliterator.Price> trySplit() {
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

  public record Price(List<Long> changes, long price) {
    @Override
    public boolean equals(final Object o) {
      if (o == null || getClass() != o.getClass()) return false;
      final Price price = (Price) o;
      return Objects.equals(changes, price.changes);
    }

    @Override
    public int hashCode() {
      return Objects.hashCode(changes);
    }
  }
}
