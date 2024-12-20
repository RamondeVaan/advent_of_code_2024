package nl.ramondevaan.aoc2024.util;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public record Coordinate(int row, int column) {

  public Coordinate() {
    this(0, 0);
  }

  public Coordinate[] neighborsWithinDistance(final int distance) {
    final var ret = new Coordinate[2 * (distance * distance + distance)];
    int index = 0, rowMinus, rowPlus;
    for (int rowDiff = 1; rowDiff <= distance; rowDiff++) {
      rowMinus = row - rowDiff;
      rowPlus = row + rowDiff;
      for (int columnDiff = 1; columnDiff <= distance - rowDiff; columnDiff++) {
        ret[index++] = new Coordinate(rowMinus, column + columnDiff);
        ret[index++] = new Coordinate(rowMinus, column - columnDiff);
        ret[index++] = new Coordinate(rowPlus, column + columnDiff);
        ret[index++] = new Coordinate(rowPlus, column - columnDiff);
      }
      ret[index++] = new Coordinate(rowMinus, column);
      ret[index++] = new Coordinate(rowPlus, column);
    }
    for (int columnDiff = 1; columnDiff <= distance; columnDiff++) {
      ret[index++] = new Coordinate(row, column + columnDiff);
      ret[index++] = new Coordinate(row, column - columnDiff);
    }

    return ret;
  }

  public Stream<Coordinate> directNeighbors() {
    return Stream.of(
        new Coordinate(row + 1, column),
        new Coordinate(row, column + 1),
        new Coordinate(row - 1, column),
        new Coordinate(row, column - 1)
    );
  }

  public Stream<Coordinate> allNeighbors() {
    return Stream.of(
        new Coordinate(row + 1, column),
        new Coordinate(row, column + 1),
        new Coordinate(row - 1, column),
        new Coordinate(row, column - 1),
        new Coordinate(row - 1, column - 1),
        new Coordinate(row + 1, column - 1),
        new Coordinate(row - 1, column + 1),
        new Coordinate(row + 1, column + 1)
    );
  }

  public Coordinate minus(final Coordinate other) {
    return new Coordinate(this.row - other.row, this.column - other.column);
  }

  public Coordinate plus(final Coordinate other) {
    return new Coordinate(this.row + other.row, this.column + other.column);
  }

  public int distanceTo(final Coordinate other) {
    return Math.abs(this.row - other.row) + Math.abs(this.column - other.column);
  }

  public static Coordinate of(int row, int column) {
    return new Coordinate(row, column);
  }

  public static Stream<Coordinate> stream(final int rowStart, final int rowEnd, final int columnStart, final int columnEnd) {
    return IntStream.range(rowStart, rowEnd).boxed()
        .flatMap(row -> IntStream.range(columnStart, columnEnd)
            .mapToObj(column -> new Coordinate(row, column)));
  }

  public static void forEach(final int rowStart, final int rowEnd, final int columnStart, final int columnEnd, final IntBiConsumer consumer) {
    for (int row = rowStart; row < rowEnd; row++) {
      for (int column = columnStart; column < columnEnd; column++) {
        consumer.consume(row, column);
      }
    }
  }

  public static Stream<Coordinate> streamClosed(final int rowStart, final int rowEnd, final int columnStart, final int columnEnd) {
    return IntStream.rangeClosed(rowStart, rowEnd).boxed()
        .flatMap(row -> IntStream.rangeClosed(columnStart, columnEnd)
            .mapToObj(column -> new Coordinate(row, column)));
  }
}
