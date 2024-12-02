package nl.ramondevaan.aoc2024.util;

public interface Parser<T, U> {
    U parse(T toParse);
}
