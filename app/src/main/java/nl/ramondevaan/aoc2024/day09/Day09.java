package nl.ramondevaan.aoc2024.day09;

import com.google.common.primitives.ImmutableIntArray;

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
    final var before = initializeBefore(disk);
    final var after = new LinkedList<File>();
    File file, next;

    outer:
    while ((file = disk.pollLast()) != null) {
      if (file.moved) {
        after.addFirst(file);
        continue;
      }

      final var it = disk.listIterator();
      boolean foundSpace = false;
      while (it.hasNext()) {
        if ((next = it.next()).isSpace()) {
          foundSpace = true;
          if (next.size < file.size) {
            continue;
          }
          it.remove();
          it.add(new File(file.id, file.size, false, true));
          after.addFirst(new File(-1, file.size, true, true));
          if (next.size > file.size) {
            it.add(new File(-1, next.size - file.size, true, true));
          }
          continue outer;
        } else if (!foundSpace) {
          it.remove();
          before.add(next);
        }
      }
      after.addFirst(file);
    }

    disk.addAll(0, before);
    disk.addAll(after);

    return calculateScore(disk);
  }

  private long calculateScore(final List<File> files) {
    long diskIndex = 0L, sum = 0L;
    for (final var file : files) {
      if (file.id > 0) {
        sum += file.id * (file.size * (file.size + 2 * diskIndex - 1)) / 2;
      }
      diskIndex += file.size;
    }

    return sum;
  }

  private LinkedList<File> initializeDisk() {
    final var ret = new LinkedList<File>();
    boolean isSpace = false;
    for (int i = 0; i < diskMap.length(); i++) {
      if (diskMap.get(i) != 0) {
        ret.add(new File(isSpace ? -1 : i / 2, diskMap.get(i), isSpace, isSpace));
      }
      isSpace = !isSpace;
    }

    return ret;
  }

  private LinkedList<File> initializeBefore(final LinkedList<File> disk) {
    final var ret = new LinkedList<File>();
    final var it = disk.listIterator();
    File next;

    while (it.hasNext()) {
      if ((next = it.next()).isSpace()) {
        return ret;
      }
      it.remove();
      ret.add(next);
    }

    return ret;
  }

  private record File(int id, int size, boolean isSpace, boolean moved) {
  }
}
