package nl.ramondevaan.aoc2024.day23;

public class MaxCliqueFinder {

  private int[] maxClique;
  private Network network;

  public int[] compute(final Network network) {
    this.network = network;
    this.maxClique = new int[0];
    computeImpl(new int[network.size], 0, network.nodes, network.size, new int[network.size], 0);
    return maxClique;
  }

  private void computeImpl(final int[] r, final int rSize, int[] p, int pSize, int[] x, int xSize) {
    if (pSize == 0 && xSize == 0 && rSize > maxClique.length) {
      maxClique = new int[rSize];
      System.arraycopy(r, 0, maxClique, 0, rSize);
      return;
    }

    for (int i = 0; i < pSize; i++) {
      final var v = p[i];
      int[] newP = new int[Math.min(pSize, network.neighborsSize[v])];
      int newPSize = intersection(p, i, pSize, newP, v);
      int[] newX = new int[newPSize + xSize];
      int newXSize = intersection(x, 0, xSize, newX, v);
      r[rSize] = v;
      computeImpl(r, rSize + 1, newP, newPSize, newX, newXSize);
      x[xSize++] = v;
    }
  }

  private int intersection(final int[] arr, int offset, int arrSize, final int[] result, final int v) {
    int size = 0;
    for (int i = offset; i < arrSize; i++) if (network.connected[v][arr[i]]) result[size++] = arr[i];
    return size;
  }
}
