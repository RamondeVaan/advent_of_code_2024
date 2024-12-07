package nl.ramondevaan.aoc2024.day07;

public enum Operator {
  ADD {
    @Override
    public long apply(final long left, final long right) {
      return left + right;
    }
  },
  MULTIPLY {
    @Override
    public long apply(final long left, final long right) {
      return left * right;
    }
  },
  CONCATENATE {
    @Override
    public long apply(final long left, final long right) {
      final var leftStr = String.valueOf(left);
      final var rightStr = String.valueOf(right);
      return Long.parseLong(leftStr + rightStr);
    }
  };

  public abstract long apply(long left, long right);
}
