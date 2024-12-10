package nl.ramondevaan.aoc2024.day09;

import com.google.common.primitives.ImmutableIntArray;
import lombok.AllArgsConstructor;

import java.util.LinkedList;
import java.util.List;

public class Day09 {

  private final ImmutableIntArray diskMap;

  public Day09(final List<String> lines) {
    final var parser = new DiskMapParser();

    this.diskMap = parser.parse(lines.getFirst());
  }

  public long solve1() {
    final var arr = diskMap.toArray();
    int lastIndex = arr.length - 1;
    long diskIndex = 0L, sum = 0L;
    for (int i = 0; i < arr.length; i++) {
      if (i % 2 == 0) {
        sum += i / 2 * (arr[i] * (arr[i] + 2 * diskIndex - 1)) / 2;
        diskIndex += arr[i];
        arr[i] = 0;
      } else {
        while (arr[i] > 0 && lastIndex >= 0) {
          int amount = Math.min(arr[i], arr[lastIndex]);
          sum += lastIndex / 2 * (amount * (amount + 2 * diskIndex - 1)) / 2;
          diskIndex += amount;
          arr[i] -= amount;
          arr[lastIndex] -= amount;
          if (arr[lastIndex] == 0) {
            lastIndex -= 2;
          }
        }
      }
    }
    return sum;
  }

  public long solve2() {
    final var disk = initializeDisk();
    final var after = new LinkedList<File>();

    outer:
    for (int id = disk.size() / 2; id > 0; id--) {
      File file = disk.removeLast();
      while (file.id != id) {
        after.addFirst(file);
        file = disk.removeLast();
      }

      final var it = disk.listIterator();
      while (it.hasNext()) {
        final var next = it.next();
        if (next.isSpace() && next.size >= file.size) {
          it.remove();
          it.add(file);
          after.addFirst(new File(-1, file.size, true));
          var newSize = next.size - file.size;
          if (newSize > 0) {
            it.add(new File(-1, newSize, true));
          }
          continue outer;
        }
      }
      after.addFirst(file);
    }

    disk.addAll(after);

    long diskIndex = 0L, sum = 0L;
    for (final var file : disk) {
      if (file.id > 0) {
        sum += file.id * (file.size * (file.size + 2 * diskIndex - 1)) / 2;
      }
      diskIndex += file.size;
    }

    return sum;
  }

  private LinkedList<File> initializeDisk() {
    final var ret = new LinkedList<File>();
    boolean isFile = true;
    for (int i = 0; i < diskMap.length(); i++) {
      ret.add(new File(isFile ? i / 2 : -1, diskMap.get(i), isFile = !isFile));
    }

    return ret;
  }

  private record File(int id, int size, boolean isSpace) {
  }
}
