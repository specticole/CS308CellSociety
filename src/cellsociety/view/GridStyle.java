package cellsociety.view;

import cellsociety.CellGrid;


public abstract class GridStyle {
  public static final int VIEW_WIDTH = 600;
  public static final int VIEW_HEIGHT = 600;

  public abstract void createGrid(int width, int height);

  public abstract void updateGrid(CellGrid cellGrid, int time);

}
