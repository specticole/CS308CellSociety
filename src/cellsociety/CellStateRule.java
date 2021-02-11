package cellsociety;

import java.util.List;

public interface CellStateRule {
  public abstract void nextCellState(Cell cell, List<Cell> neighbors);
  public abstract void getGameSpecifics();
}
