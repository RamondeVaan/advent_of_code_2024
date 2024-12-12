package nl.ramondevaan.aoc2024.day12;

public record Region(int value, long area, long perimeter, long corners) {
  public static final class Builder {
    public final int value;
    private long area = 0;
    private long perimeter = 0;
    private long corners = 0;

    public Builder(final int value) {
      this.value = value;
    }

    public Builder incrementArea() {
      area++;
      return this;
    }

    public Builder incrementPerimeter() {
      perimeter++;
      return this;
    }

    public Builder incrementCorners() {
      corners++;
      return this;
    }

    public Region build() {
      final var ret = new Region(value, area, perimeter, corners);

      return ret;
    }
  }
}
