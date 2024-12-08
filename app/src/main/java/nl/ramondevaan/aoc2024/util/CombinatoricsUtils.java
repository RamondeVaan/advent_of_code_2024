package nl.ramondevaan.aoc2024.util;

import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CombinatoricsUtils {
  public static <T> Stream<ImmutablePair<T, T>> objectPairs(final List<T> list) {
    return IntStream.range(0, list.size() - 1)
        .boxed()
        .flatMap(i -> {
          final T left = list.get(i);

          return IntStream.range(i + 1, list.size()).mapToObj(list::get)
              .map(right -> new ImmutablePair<>(left, right));
        });
  }

  public static Stream<Pair> pairs(int size) {
    return IntStream.range(0, size).boxed()
        .flatMap(left -> IntStream.range(left + 1, size)
            .mapToObj(right -> new Pair(left, right)));
  }

  public static Stream<Pair> allPairs(int size) {
    return pairs(size).flatMap(pair -> Stream.of(pair, new Pair(pair.right(), pair.left())));
  }
}
