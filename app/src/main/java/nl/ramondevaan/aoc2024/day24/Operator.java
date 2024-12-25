package nl.ramondevaan.aoc2024.day24;

public enum Operator {
  AND {
    @Override
    public boolean apply(final boolean left, final boolean right) {
      return left && right;
    }
  }, OR {
    @Override
    public boolean apply(final boolean left, final boolean right) {
      return left || right;
    }
  }, XOR {
    @Override
    public boolean apply(final boolean left, final boolean right) {
      return left != right;
    }
  };

  public abstract boolean apply(final boolean left, final boolean right);
}
