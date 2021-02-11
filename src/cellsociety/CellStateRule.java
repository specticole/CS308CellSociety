package cellsociety;

import java.util.List;

public interface CellStateRule {

  /**
   * This method is called every step, and updates the cell's state under certain conditions
   *
   * @param cell this is the cell which's state will be updated
   * @param neighbors this is all 8 neighbors of cell
   */
  public abstract void nextCellState(Cell cell, List<Cell> neighbors);

}
