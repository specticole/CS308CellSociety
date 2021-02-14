package cellsociety.grids;

import cellsociety.*;
import java.util.*;
import java.lang.reflect.Constructor;
import java.util.stream.Collectors;

/**
 * Fixed-size, dense 2D grid that implements a map from [0,w)*[0,h] ->
 * Cell. Can be used to implement both wrapping and non-wrapping
 * rectangular and hex grids.
 *
 * A dense 2D grid has a certain boundary behavior specified at
 * initialization -- either wrapping or non-wrapping.
 *
 * @author Franklin Wei
 */
public abstract class Dense2DCellGrid extends CellGrid {
  int width, height;
  Cell cells[][];
  boolean wrapping;

  public Dense2DCellGrid(int w, int h, boolean wrapping) {
    cells = new Cell[h][w];
    this.wrapping = wrapping;

    for(int y = 0; y < h; y++)
      for(int x = 0; x < w; x++)
        cells[y][x] = new Cell(this, new GridCoordinates(x, y));
  }

  /**
   * Retrieve the cell associated with the given coordinates.
   *
   * If coords falls in the "normal range" of [0, w) * [0, h), then
   * the behavior is the same no matter if the grid is wrapping or
   * non-wrapping. However, the behavior of this method depends on the
   * wrapping behavior when coords falls outside of the normal
   * range. If wrapping is disabled, all out-of-bounds queries return
   * null. However, if wrapping is enabled, this method transparently
   * reduces the coordinates to the quotient ring that corresponds to
   * the normal range, and returns non-null.
   */
  @Override
  public Cell getCell(GridCoordinates coords) {
    return cells[coords.getY()][coords.getX()];
  }

  @Override
  public List<Cell> getNeighbors(Cell c) {
    return getNeighborCoordinates(c.getCoordinates())
        .stream()
        .map(coords -> this.getCell(coords))
        .filter(cell -> cell != null)
        .collect(Collectors.toList());
  }

  private class Dense2DGridIterator implements Iterator<Cell> {
    private final Cell grid[][];
    private int index = 0;
    private final int total;

    public Dense2DGridIterator(Cell grid[][]) {
      this.grid = grid;
      this.index = 0;
      this.total = grid.length * grid[0].length;
    }

    @Override
    public boolean hasNext() {
      return index < total;
    }

    @Override
    public void remove() {
      index++;
    }

    @Override
    public Cell next() {
      return grid[index / grid[0].length][index % grid[0].length];
    }
  }

  @Override
  public Iterator<Cell> iterator() {
    return new Dense2DGridIterator(cells);
  }
}
