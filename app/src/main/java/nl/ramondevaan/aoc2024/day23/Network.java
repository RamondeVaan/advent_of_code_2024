package nl.ramondevaan.aoc2024.day23;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Network {
  private final boolean[] present;
  private final boolean[][] edgeExists;
  private final int[][] neighbors;
  private final int[] degree;
  private final int[] vertices;
  private final int vertexCount;

  private Network(final boolean[] present, final boolean[][] edgeExists, final int[][] neighbors, final int[] degree, final int[] vertices, final int vertexCount) {
    this.present = present;
    this.edgeExists = edgeExists;
    this.neighbors = neighbors;
    this.degree = degree;
    this.vertices = vertices;
    this.vertexCount = vertexCount;
  }

  public IntStream vertexStream() {
    return Arrays.stream(vertices, 0, vertexCount);
  }

  public boolean containsVertex(final int v) {
    return v >= 0 && v < vertexCount && present[v];
  }

  public boolean containsEdge(final int u, final int v) {
    return edgeExists[u][v];
  }

  public int[] vertices() {
    final var vertices = new int[vertexCount];
    System.arraycopy(this.vertices, 0, vertices, 0, vertexCount);
    return vertices;
  }

  public int vertexCount() {
    return vertexCount;
  }

  public int degree(final int v) {
    return degree[v];
  }

  public int[] neighbors(final int v) {
    final var neighbors = new int[degree[v]];
    System.arraycopy(this.neighbors[v], 0, neighbors, 0, neighbors.length);
    return neighbors;
  }

  public IntStream neighborStream(final int v) {
    return Arrays.stream(neighbors[v], 0, degree[v]);
  }

  public static Builder builder(final int vertexUniverseCount) {
    return new Builder(vertexUniverseCount);
  }

  public final static class Builder {
    private boolean[] present;
    private boolean[][] edgeExists;
    private int[][] neighbors;
    private int[] degree;
    private int[] vertices;
    private int vertexCount;

    private Builder(final int vertexUniverseCount) {
      this.present = new boolean[vertexUniverseCount];
      this.edgeExists = new boolean[vertexUniverseCount][vertexUniverseCount];
      this.neighbors = new int[vertexUniverseCount][vertexUniverseCount];
      this.degree = new int[vertexUniverseCount];
      this.vertices = new int[vertexUniverseCount];
      this.vertexCount = 0;
    }

    public Builder add(final int x, final int y) {
      if (!present[x]) vertices[vertexCount++] = x;
      if (!present[y]) vertices[vertexCount++] = y;
      if (!edgeExists[x][y]) neighbors[x][degree[x]++] = y;
      if (!edgeExists[y][x]) neighbors[y][degree[y]++] = x;
      edgeExists[x][y] = true;
      edgeExists[y][x] = true;
      present[x] = true;
      present[y] = true;
      return this;
    }

    public Network build() {
      final var network = new Network(present, edgeExists, neighbors, degree, vertices, vertexCount);
      this.present = null;
      this.edgeExists = null;
      this.neighbors = null;
      this.degree = null;
      this.vertices = null;
      return network;
    }
  }
}
