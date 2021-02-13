package cellsociety.view;

import cellsociety.CellGrid;
import javafx.scene.layout.Pane;


public abstract class GridStyle {
  public static final int VIEW_WIDTH = 500;
  public static final int VIEW_HEIGHT = 500;

  public abstract void createGrid(int width, int height);

  public abstract void updateGrid(CellGrid cellGrid, int time);

}
