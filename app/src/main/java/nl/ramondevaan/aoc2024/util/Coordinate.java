package nl.ramondevaan.aoc2024.util;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public record Coordinate(int row, int column) {

  public Coordinate() {
    this(0, 0);
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

  public static Coordinate of(int row, int column) {
    return new Coordinate(row, column);
  }

  public static Stream<Coordinate> streamClosed(final int rowStart, final int rowEnd, final int columnStart, final int columnEnd) {
    return IntStream.rangeClosed(rowStart, rowEnd).boxed()
        .flatMap(row -> IntStream.rangeClosed(columnStart, columnEnd)
            .mapToObj(column -> new Coordinate(row, column)));
  }
}
