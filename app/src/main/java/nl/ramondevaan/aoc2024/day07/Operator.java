package nl.ramondevaan.aoc2024.day07;

import com.google.common.math.LongMath;

import java.math.RoundingMode;

public enum Operator {
  ADD {
    @Override
    public long reverseApply(final long result, final long right) {
      return result - right;
    }
  },
  MULTIPLY {
    @Override
    public long reverseApply(final long result, final long right) {
      if (result % right == 0) {
        return result / right;
      }
      return -1;
    }
  },
  CONCATENATE {
    @Override
    public long reverseApply(final long result, final long right) {
      final var rightLog10 = LongMath.pow(10, LongMath.log10(right, RoundingMode.FLOOR) + 1);

      if (result % rightLog10 == right) {
        return result / rightLog10;
      }

      return -1;
    }
  };

  public abstract long reverseApply(long result, long right);
}
