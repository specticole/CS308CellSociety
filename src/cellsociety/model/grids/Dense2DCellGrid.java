package cellsociety.model.grids;

import cellsociety.model.Cell;
import cellsociety.model.CellGrid;
import cellsociety.model.CellState;
import cellsociety.model.GridCoordinates;
import java.util.*;
import java.util.stream.Stream;
import java.util.stream.Collectors;

/**
 * Fixed-size, dense 2D grid that implements a map from [0,w)*[0,h] ->
 * Cell. Can be used to implement both wrapping and non-wrapping
 * rectangular and hex grids.
 *
 * A dense grid has the special property that it can be represented as
 * a rectangular 2D matrix, and so this class exposes functionality to
 * mass-set (setAllStates()) and mass-retrieve (getAllStates()) the
 * CellStates of all Cells at a certain time.
 *
 * Each grid has a certain boundary behavior specified at
 * initialization -- either wrapping or non-wrapping.
 *
 * @author Franklin Wei
 */
public abstract class Dense2DCellGrid extends CellGrid {
  private int width, height;
  private Cell cells[][];
  private boolean wrapping;

  /**
   * Initialize a dense 2D grid of width `w' and height
   * `h'. Optionally wrapping.
   *
   * @param w Width.
   * @param h Height.
   * @param wrapping Whether to wrap.
   */
  public Dense2DCellGrid(int w, int h, boolean wrapping) {
    System.out.printf("Construct %dx%d grid\n", w, h);
    width = w;
    height = h;
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
   * non-wrapping.
   *
   * However, the behavior of this method depends on the wrapping
   * behavior when coords falls outside of the normal range. If wrapping is disabled, all out-of-bounds queries return
   * null. However, if wrapping is enabled, this method transparently
   * reduces the coordinates to the quotient ring that corresponds to
   * the normal range, and returns non-null.
   */
  @Override
  public Cell getCell(GridCoordinates coords) {
    int x = coords.getX();
    int y = coords.getY();

    boolean inBounds = (0 <= x && x < width) && (0 <= y && y < height);

    if(!inBounds) {
      if(!wrapping)
        return null;

      // Java's modulus yields a negative value when x is negative; see:
      //
      // https://stackoverflow.com/questions/4403542/how-does-java-do-modulus-calculations-with-negative-numbers
      //
      // To fix this we add width and modulo again to get it into the
      // range [0, width).
      x = ((x % width) + width) % width;
      y = ((y % height) + height) % height;
    }

    return cells[y][x];
  }

  /**
   * Retrieve a Stream containing the neighbor offsets from a given
   * center. No handling of edge cases (i.e. wrapping, bounds
   * checking) needs to be done by implementing classes -- this is all
   * handled by Dense2DCellGrid.
   *
   * Not all grid types care about the location when computing
   * neighbor offsets -- for example, Rectangular grids can ignore
   * them, since the set of neighbor offsets is the same for all grid
   * locations. However, hexagonal grids do care about this.
   *
   * @param center Central cell's GridCoordinates.
   * @return Stream of neighbor GridCoordinates.
   */
  abstract public Stream<GridCoordinates> getNeighborOffsets(GridCoordinates center);

  @Override
  public Collection<GridCoordinates> getNeighborCoordinates(GridCoordinates center) {
    return getNeighborOffsets(center)
        .map(offs -> offs.add(center))
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
      Cell c = grid[index / grid[0].length][index % grid[0].length];
      this.remove();
      return c;
    }
  }

  @Override
  public Iterator<Cell> iterator() {
    return new Dense2DGridIterator(cells);
  }

  /**
   * Append states[i][j] to cells[i][j].states, and increment the
   * simulation time.
   *
   * `states' must be a rectangular array of the same size as `cells'
   * (i.e. width*height).
   *
   * @param states 2D array in row-major order representing the states
   * of each Cell to append.
   */
  public void appendStates(CellState states[][]) {
    assert(states.length == height);
    for(int y = 0; y < height; y++) {
      assert(states[y].length == width);

      for(int x = 0; x < width; x++)
        cells[y][x].appendState(states[y][x]);
    }

    advanceCurrentTime();
  }

  /**
   * Extract a snapshot all CellStates at time `delta + currentTime'.
   *
   * @param delta Time delta at which to retrieve a snapshot.
   * @return 2D array in row-major order containing all CellStates
   * at the given time delta.
   */
  public CellState[][] extractStates(int delta) {
    System.out.printf("Extract at delta=%d -> %dx%d\n", delta, width, height);
    CellState states[][] = new CellState[height][width];

    for(int y = 0; y < height; y++) {
      for(int x = 0; x < width; x++) {
        states[y][x] = getCell(new GridCoordinates(x, y)).getState(delta);
      }
    }

    return states;
  }
}
