package nl.ramondevaan.aoc2024.util;

import com.google.common.collect.Multiset;
import com.google.common.math.IntMath;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntFunction;
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

  public static <T> Stream<T[]> multisetPermutations(final Multiset<T> multiset, IntFunction<T[]> newArr) {
    final var entries = multiset.entrySet().stream().toList();
    final var count = entries.stream().mapToInt(Multiset.Entry::getCount).toArray();
    final int total = entries.stream().mapToInt(Multiset.Entry::getCount).sum();
    final int div = entries.stream().mapToInt(Multiset.Entry::getCount).map(IntMath::factorial).reduce(1, (a, b) -> a * b);
    final int numberOfPermutations = IntMath.factorial(total) / div;
    final var results = new ArrayList<int[]>(numberOfPermutations);
    multisetPermutations(count, new int[total], 0, results);
    return results.stream().map(arr -> Arrays.stream(arr)
        .mapToObj(entries::get).map(Multiset.Entry::getElement).toArray(newArr));
  }

  private static void multisetPermutations(final int[] multiset, final int[] current, final int index, final List<int[]> result) {
    if (index == current.length) {
      final int[] t = new int[current.length];
      System.arraycopy(current, 0, t, 0, current.length);
      result.add(t);
      return;
    }
    for (int i = 0; i < multiset.length; i++) {
      if (multiset[i] == 0) continue;
      multiset[i]--;
      current[index] = i;
      multisetPermutations(multiset, current, index + 1, result);
      multiset[i]++;
    }
  }
}
