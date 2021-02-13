package cellsociety.cells;

import cellsociety.Cell;
import cellsociety.CellGrid;
import cellsociety.CellState;
import cellsociety.CellStateList;
import cellsociety.states.GameOfLifeState;

public class GameOfLifeCell extends Cell {
  private CellState states;
  private CellStateList priorStates;

  public GameOfLifeCell(String gameType, CellGrid parent) {
    super(parent);
    states = new GameOfLifeState();
  }
}
