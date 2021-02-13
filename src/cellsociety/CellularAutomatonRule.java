package cellsociety;

import java.util.List;
import java.util.Map;

/**
 * Interface that must be implemented
 */
public abstract class CellularAutomatonRule {
  protected Map<String, String> parameters;

  public CellularAutomatonRule(Map<String, String> params) {
    this.parameters = params;
  }

  /**
   * This method is called every step, and updates the cell's state under certain conditions
   *
   * @param cell this is the cell which's state will be updated
   * @param neighbors this is all 8 neighbors of cell
   */
  public abstract void advanceCellState(Cell cell, List<Cell> neighbors);
}
