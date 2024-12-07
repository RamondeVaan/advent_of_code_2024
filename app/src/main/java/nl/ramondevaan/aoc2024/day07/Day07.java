package nl.ramondevaan.aoc2024.day07;

import java.util.List;

import static nl.ramondevaan.aoc2024.day07.Operator.*;

public class Day07 {

  private final static List<Operator> OPERATORS_1 = List.of(ADD, MULTIPLY);
  private final static List<Operator> OPERATORS_2 = List.of(ADD, MULTIPLY, CONCATENATE);
  private final List<Equation> equations;

  public Day07(final List<String> lines) {
    final var parser = new EquationParser();
    this.equations = lines.stream().map(parser::parse).toList();
  }

  public long solve1() {
    return solve(OPERATORS_1);
  }

  public long solve2() {
    return solve(OPERATORS_2);
  }

  private long solve(final List<Operator> operators) {
    return equations.stream()
        .filter(equation -> isSolvable(equation, operators))
        .mapToLong(Equation::testValue)
        .sum();
  }

  private static boolean isSolvable(final Equation equation, final List<Operator> operators) {
    return isSolvable(operators, equation.testValue(), equation.numbers().toArray(), equation.numbers().length() - 1);
  }

  private static boolean isSolvable(final List<Operator> operators, final long value, final long[] operands, final int operandIndex) {
    if (operandIndex == -1) {
      return value == 0;
    }

    final var currentOperand = operands[operandIndex];
    for (final var operator : operators) {
      final var newValue = operator.reverseApply(value, currentOperand);
      if (operator.reverseApply(value, operands[operandIndex]) >= 0 &&
          isSolvable(operators, newValue, operands, operandIndex - 1)) {
        return true;
      }
    }
    return false;
  }
}
