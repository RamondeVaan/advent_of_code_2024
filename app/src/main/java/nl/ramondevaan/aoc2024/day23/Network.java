package nl.ramondevaan.aoc2024.day23;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Network {
  public final static int SIZE = 26 * 26;
  public final boolean[] present;
  public final boolean[][] connected;
  public final int[][] neighbors;
  public final int[] neighborsSize;
  public final int[] nodes;
  public int size;

  public Network() {
    present = new boolean[SIZE];
    connected = new boolean[SIZE][SIZE];
    this.neighbors = new int[SIZE][SIZE];
    this.neighborsSize = new int[SIZE];
    this.nodes = new int[SIZE];
    this.size = 0;
  }

  public void add(final int x, final int y) {
    if (!present[x]) nodes[size++] = x;
    if (!present[y]) nodes[size++] = y;
    if (!connected[x][y]) neighbors[x][neighborsSize[x]++] = y;
    if (!connected[y][x]) neighbors[y][neighborsSize[y]++] = x;
    connected[x][y] = true;
    connected[y][x] = true;
    present[x] = true;
    present[y] = true;
  }

  public IntStream nodes() {
    return Arrays.stream(nodes, 0, size);
  }

  public IntStream neighbors(final int x) {
    return Arrays.stream(neighbors[x], 0, neighborsSize[x]);
  }
}
