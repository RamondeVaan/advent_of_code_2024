package nl.ramondevaan.aoc2024.day07;

import com.google.common.math.LongMath;

import java.math.RoundingMode;

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
      return LongMath.pow(10, LongMath.log10(right, RoundingMode.FLOOR) + 1) * left + right;
    }
  };

  public abstract long apply(long left, long right);
}
