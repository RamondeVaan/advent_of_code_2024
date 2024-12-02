package nl.ramondevaan.aoc2024.util;

@FunctionalInterface
public interface LongBiPredicate {
    boolean test(long left, long right);
}
