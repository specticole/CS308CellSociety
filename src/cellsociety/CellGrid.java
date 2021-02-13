package cellsociety;

import java.util.Iterator;
import java.util.List;

/**
 * A CellGrid is an abstract collection of Cells laid out on a certain
 * geometric pattern. Derived classes will define the geometric layout
 * of the grid by implementing getNeighbors(). Deriving classes should
 * also implement a non-default constructor that allows creation of
 * empty grids of a specified size; the specifics of this are left to
 * deriving classes.
 *
 * Iterating over a CellGrid is possible; doing so will yield a series
 * of Cells.
 *
 * @author Franklin Wei
 */
public abstract class CellGrid implements java.lang.Iterable {
  protected List<Cell> myCells;
  private int currentTime;

  /**
   * Initialize an empty grid at time T=0.
   */
  public CellGrid() {
    this.currentTime = 0;
  }

  /**
   * Retrieve a list of the adjacent neighbors of `cell'. Implementing
   * classes should define the neighbor index layout.
   *
   * @param cell central cell
   * @return list of neighbors of `xcell'
   */
  abstract public List getNeighbors(Cell cell);

  @Override
  public Iterator iterator() {
    return cells.iterator();
  }

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
    return currentTime;
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
