package nl.ramondevaan.aoc2024.day15;

import nl.ramondevaan.aoc2024.util.Coordinate;
import nl.ramondevaan.aoc2024.util.IntMap;
import nl.ramondevaan.aoc2024.util.Parser;

public class WideWarehouseParser implements Parser<Warehouse, Warehouse> {
  @Override
  public Warehouse parse(final Warehouse warehouse) {
    final var map = warehouse.map();
    final var builder = IntMap.builder(map.rows(), map.columns() * 2);

    map.streamEntries()
        .forEach(entry -> {
          switch (entry.value()) {
            case Warehouse.WALL -> {
              builder.set(entry.coordinate().row(), entry.coordinate().column() * 2, Warehouse.WALL);
              builder.set(entry.coordinate().row(), entry.coordinate().column() * 2 + 1, Warehouse.WALL);
            }
            case Warehouse.BOX -> {
              builder.set(entry.coordinate().row(), entry.coordinate().column() * 2, Warehouse.BOX_LEFT);
              builder.set(entry.coordinate().row(), entry.coordinate().column() * 2 + 1, Warehouse.BOX_RIGHT);
            }
          }
        });

    final var robot = new Coordinate(warehouse.robot().row(), warehouse.robot().column() * 2);
    return new Warehouse(builder.build(), robot);
  }
}
