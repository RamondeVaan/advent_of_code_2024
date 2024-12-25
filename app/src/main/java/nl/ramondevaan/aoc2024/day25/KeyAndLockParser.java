package nl.ramondevaan.aoc2024.day25;

import nl.ramondevaan.aoc2024.util.BlankStringPartitioner;
import nl.ramondevaan.aoc2024.util.Parser;
import nl.ramondevaan.aoc2024.util.Partitioner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public class KeyAndLockParser implements Parser<List<String>, KeysAndLocks> {

  private final Partitioner<String> partitioner = new BlankStringPartitioner();

  @Override
  public KeysAndLocks parse(final List<String> toParse) {
    final var keys = new ArrayList<List<Integer>>();
    final var locks = new ArrayList<List<Integer>>();

    for (final var entry : partitioner.partition(toParse)) {
      final var heights = new int[entry.getFirst().length()];
      for (final var line : entry) for (int i = 0; i < line.length(); i++) if (line.charAt(i) == '#') heights[i]++;
      final var result = Arrays.stream(heights).map(i -> i - 1).boxed().toList();
      if (entry.getFirst().chars().allMatch(c -> c == '#')) locks.add(result);
      else keys.add(result);
    }

    return new KeysAndLocks(unmodifiableList(keys), unmodifiableList(locks));
  }
}
