package nl.ramondevaan.aoc2024.day24;

import nl.ramondevaan.aoc2024.util.BlankStringPartitioner;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.Comparator.comparing;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.*;

public class Day24 {

  private final List<Wire> wires;
  private final List<Gate> gates;
  private final Map<String, List<Gate>> gatesByInput;
  private final Map<String, Gate> gatesByOutput;
  private final List<Gate> zGates;

  public Day24(final List<String> lines) {
    final var partitioner = new BlankStringPartitioner();
    final var wireParser = new WireParser();
    final var gateParser = new GateParser();

    final var partitions = partitioner.partition(lines);
    this.wires = partitions.getFirst().stream().map(wireParser::parse).toList();
    this.gates = partitions.get(1).stream().map(gateParser::parse).toList();
    this.gatesByOutput = Collections.unmodifiableMap(gates.stream()
        .collect(Collectors.toMap(Gate::getResultId, Function.identity())));
    this.zGates = gates.stream().filter(Gate::isOutputGate).sorted(comparing(Gate::getResultId)).toList();
    this.gatesByInput = Collections.unmodifiableMap(gates.stream()
        .flatMap(gate -> gate.getInputs().map(input -> ImmutablePair.of(input, gate)))
        .collect(groupingBy(ImmutablePair::getLeft, mapping(ImmutablePair::getRight, toList()))));
  }

  public long solve1() {
    final var knownValues = new HashMap<String, Boolean>();
    for (final var wire : wires) knownValues.put(wire.id(), wire.value());
    return solve(knownValues);
  }

  private long solve(final Map<String, Boolean> values) {
    var result = 0L;
    for (int i = 0; i < zGates.size(); i++) if (solve(zGates.get(i), values)) result |= (1L << i);
    return result;
  }

  private boolean solve(final Gate gate, final Map<String, Boolean> values) {
    var result = values.get(gate.getResultId());
    if (result != null) return result;

    Boolean left = values.get(gate.getLeftId()), right = values.get(gate.getRightId());
    if (left == null) left = solve(gatesByOutput.get(gate.getLeftId()), values);
    if (right == null) right = solve(gatesByOutput.get(gate.getRightId()), values);

    if ((result = values.get(gate.getResultId())) != null) return result;
    values.put(gate.getResultId(), (result = gate.getOperator().apply(left, right)));
    return result;
  }

  public String solve2() {
    final var wrongWires = new HashSet<String>();
    final var xIds = wires.stream().map(Wire::id).filter(id -> id.startsWith("x")).sorted().toList();

    wrongWires.addAll(getWrongXORWires());
    wrongWires.addAll(getWrongZWires());
    wrongWires.addAll(getWrongCarryWires());
    wrongWires.addAll(getWrongResultWires());
    gatesByInput.get(xIds.getFirst()).stream().filter(Gate::isXOR).filter(not(zGates.getFirst()::equals))
        .map(Gate::getResultId).forEach(wrongWires::add);

    return wrongWires.stream().sorted().collect(Collectors.joining(","));
  }

  private Set<String> getWrongXORWires() {
    return gates.stream()
        .filter(Gate::isXOR)
        .filter(gate -> !gate.isInputGate() && !gate.isOutputGate())
        .map(Gate::getResultId).collect(toUnmodifiableSet());
  }

  private Set<String> getWrongZWires() {
    final var wrongWires = new HashSet<String>();
    Optional.of(zGates.getLast()).filter(not(Gate::isOR)).map(Gate::getResultId).ifPresent(wrongWires::add);
    zGates.stream().limit(zGates.size() - 1)
        .filter(not(Gate::isXOR))
        .map(Gate::getResultId).forEach(wrongWires::add);
    return wrongWires;
  }

  private Set<String> getWrongCarryWires() {
    final var wrongWires = new HashSet<String>();
    final var xIds = wires.stream().map(Wire::id).filter(id -> id.startsWith("x")).sorted().toList();

    final var carryStream = xIds.stream().skip(1).flatMap(xId -> gatesByInput.getOrDefault(xId, emptyList()).stream())
        .filter(Gate::isAND).map(Gate::getResultId);
    final var carry2Stream = gates.stream().filter(Gate::isAND).filter(not(Gate::isInputGate)).map(Gate::getResultId);
    Stream.concat(carryStream, carry2Stream).filter(carryId -> {
      final var gates = gatesByInput.getOrDefault(carryId, emptyList());
      return gates.size() != 1 || !gates.getFirst().isOR();
    }).forEach(wrongWires::add);

    return wrongWires;
  }

  private Set<String> getWrongResultWires() {
    final var xIds = wires.stream().map(Wire::id).filter(id -> id.startsWith("x")).sorted().toList();

    return xIds.stream().skip(1).flatMap(xId -> gatesByInput.getOrDefault(xId, emptyList()).stream())
        .filter(Gate::isXOR).map(Gate::getResultId).filter(carryId -> {
          final var gates = gatesByInput.getOrDefault(carryId, emptyList());
          return gates.size() != 2 || gates.stream().anyMatch(Gate::isOR);
        }).collect(toUnmodifiableSet());
  }
}
