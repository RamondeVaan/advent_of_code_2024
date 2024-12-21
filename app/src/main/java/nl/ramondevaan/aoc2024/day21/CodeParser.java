package nl.ramondevaan.aoc2024.day21;

import nl.ramondevaan.aoc2024.util.Parser;
import nl.ramondevaan.aoc2024.util.StringIteratorParser;

public class CodeParser implements Parser<String, Code> {
  @Override
  public Code parse(final String toParse) {
    final var parser = new StringIteratorParser(toParse);
    final var value = parser.parseInteger();
    final var buttons = toParse.chars().mapToObj(this::parse).toList();
    return new Code(toParse, value, buttons);
  }

  public KeypadButton parse(final int toParse) {
    return switch (toParse) {
      case '0' -> NumericKeypadButton._0;
      case '1' -> NumericKeypadButton._1;
      case '2' -> NumericKeypadButton._2;
      case '3' -> NumericKeypadButton._3;
      case '4' -> NumericKeypadButton._4;
      case '5' -> NumericKeypadButton._5;
      case '6' -> NumericKeypadButton._6;
      case '7' -> NumericKeypadButton._7;
      case '8' -> NumericKeypadButton._8;
      case '9' -> NumericKeypadButton._9;
      case 'A' -> NumericKeypadButton.A;
      default -> throw new IllegalArgumentException("Invalid numeric keypad button");
    };
  }
}
