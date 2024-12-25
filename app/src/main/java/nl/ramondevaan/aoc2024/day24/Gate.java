package nl.ramondevaan.aoc2024.day24;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.stream.Stream;

@RequiredArgsConstructor
@Getter
public class Gate {

  private final String leftId;
  private final String rightId;
  private final String resultId;
  private final Operator operator;
  private final boolean isInputGate;
  private final boolean isOutputGate;

  public Gate(final String leftId, final String rightId, final String resultId, final Operator operator) {
    this.leftId = leftId;
    this.rightId = rightId;
    this.resultId = resultId;
    this.operator = operator;
    this.isInputGate = StringUtils.startsWithAny(leftId, "x", "y") ||
        StringUtils.startsWithAny(rightId, "x", "y");
    this.isOutputGate = resultId.startsWith("z");
  }

  public Stream<String> getInputs() {
    return Stream.of(leftId, rightId);
  }

  public boolean isXOR() {
    return operator == Operator.XOR;
  }

  public boolean isAND() {
    return operator == Operator.AND;
  }

  public boolean isOR() {
    return operator == Operator.OR;
  }
}
