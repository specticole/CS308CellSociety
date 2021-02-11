package View;

import cellsociety.CellGrid;

public abstract class GridStyle {

  public abstract void createGrid(int width, int height);

  public abstract void updateGrid(CellGrid cellGrid, int time);

}
