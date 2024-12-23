package nl.ramondevaan.aoc2024.day23;

public class MaxCliqueFinder {

  private int[] maxClique;
  private Network network;

  public int[] compute(final Network network) {
    this.network = network;
    this.maxClique = new int[0];
    final int vertexCount = network.vertexCount();
    computeImpl(new int[vertexCount], 0, network.vertices(), vertexCount, new int[vertexCount], 0);
    return maxClique;
  }

  private void computeImpl(final int[] r, final int rSize, final int[] p, final int pSize, final int[] x, int xSize) {
    if (pSize == 0 && xSize == 0 && rSize > maxClique.length) {
      maxClique = new int[rSize];
      System.arraycopy(r, 0, maxClique, 0, rSize);
      return;
    }

    for (int i = 0; i < pSize; i++) {
      final var v = p[i];
      final var newP = new int[Math.min(pSize, network.degree(v))];
      final var newPSize = intersection(p, i, pSize, newP, v);
      final var newX = new int[newPSize + xSize];
      final var newXSize = intersection(x, 0, xSize, newX, v);
      r[rSize] = v;
      computeImpl(r, rSize + 1, newP, newPSize, newX, newXSize);
      x[xSize++] = v;
    }
  }

  private int intersection(final int[] arr, int arrOffset, int arrSize, final int[] result, final int vertex) {
    var size = 0;
    for (int i = arrOffset; i < arrSize; i++) if (network.containsEdge(vertex, arr[i])) result[size++] = arr[i];
    return size;
  }
}
