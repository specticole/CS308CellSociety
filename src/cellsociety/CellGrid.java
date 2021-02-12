package cellsociety;

import java.util.Iterator;
import java.util.List;

/**
 * A CellGrid is an abstract collection of Cells laid out on a certain
 * geometric pattern. Derived classes will define the geometric layout
 * of the grid by implementing getNeighbors().
 *
 * Iterating over a CellGrid is possible; doing so will yield a series
 * of Cells.
 *
 * @author Franklin Wei
 */
abstract public class CellGrid implements java.lang.Iterable {
  protected List<Cell> myCells;
  private int currentTime;

  public CellGrid() {
    this.currentTime = 0;
  }

  abstract public List getNeighbors(Cell cell);

  @Override
  public Iterator iterator() {
    return myCells.iterator();
  }

  public int getCurrentTime() {
    return currentTime;
  }

  public void advanceCurrentTime() {
    currentTime++;
  }

  public void copyState() {
    for(Cell c : this) {
      c.copyState();
    }
  }
}
