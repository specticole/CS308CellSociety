package cellsociety.model;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A CellGrid is an abstract collection of Cells laid out on a certain
 * geometric pattern. Derived classes will define the geometric layout
 * of the grid by implementing getNeighbors(). Deriving classes should
 * also implement a non-default constructor that allows creation of
 * empty grids of a specified size; the specifics of this are left to
 * deriving classes.
 *
 * Iterating over a CellGrid is allowed through the standard Iterator
 * interface; doing so will yield a series of Cells.
 *
 * @author Franklin Wei
 */
public abstract class CellGrid implements java.lang.Iterable<Cell> {
  private int currentTime;

  /**
   * Initialize an empty grid at time T=-1.
   */
  public CellGrid() {
    this.currentTime = -1;
  }

  /**
   * Retrieve a list of the adjacent neighbors of `cell'.
   *
   * @param cell Central cell.
   * @return List of neighbors of `cell'
   */
  public List<Cell> getNeighbors(Cell center) {
    return getNeighborCoordinates(center.getCoordinates())
        .stream()
        .map(coords -> this.getCell(coords)) // handles wrapping and bounds check
        .filter(cell -> cell != null)        // filter out-of-bounds
        .collect(Collectors.toList());
  }

  /**
   * Retrieve the coordinates of cells neighboring the cell with
   * coordinates `coords'. The topological structure of a grid is
   * defined by this method.
   *
   * This method is allowed to return coordinates which are "out of
   * bounds", but only if the implementing class also handles this by
   * making getCell() behave appropriately when called with these "out
   * of bounds" GridCoordinates. See the the getCell() description for
   * the definition of "appropriate behavior".
   *
   * @param coords GridCoordinates of center cell.
   * @return A Collection of the GridCoordinates of neighboring cells.
   */
  abstract protected Collection<GridCoordinates> getNeighborCoordinates(GridCoordinates coords);

  /**
   * Attempt to retrieve the Cell with GridCoordinates `coords'.
   *
   * If `coords' is in bounds, then this method must return the Cell with those exact coordinates. However, if `coords' is out of bounds, then the implementing class is free to choose an "appropriate behavior", which can either be: 1) wrapping, or 2) returning null.
   *
   * @param coords Requested cell coordinates.
   * @return Cell with those coordinates, or null.
   */
  abstract public Cell getCell(GridCoordinates coords);

  /**
   * Retrieve the current generation time (starting from zero).
   *
   * @return currentTime
   */
  public int getCurrentTime() {
    return currentTime;
  }

  /**
   * Increments currentTime.
   */
  public void advanceCurrentTime() {
    currentTime++;
  }

  /**
   * Copy all cell states from time T to T + 1.
   *
   * This is used by CellularAutomaton::step to begin the process of
   * stepping to the next simulation generation.
   */
  public void copyState() {
    for(Cell c : this) {
      c.copyState();
    }
  }
}
