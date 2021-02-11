package cellsociety;

import java.util.List;

public interface CellStateRule {
  public abstract CellState nextCellState(Cell cell, List neighbors);
}
