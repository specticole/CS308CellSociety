package cellsociety;

import java.util.List;

public interface CellularAutomatonRule {
  public abstract CellState advanceCellState(Cell cell);
}
