package cellsociety.cells;

import cellsociety.Cell;
import cellsociety.CellGrid;
import cellsociety.CellState;
import cellsociety.GridCoordinates;
import cellsociety.states.GameOfLifeState;

public class GameOfLifeCell extends Cell {
  private CellState states;


  public GameOfLifeCell(CellGrid parentGrid, GridCoordinates coordinates) {
    super(parentGrid, coordinates);

    states = new GameOfLifeState();
  }
}
