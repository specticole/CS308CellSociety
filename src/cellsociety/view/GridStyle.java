package cellsociety.view;

import cellsociety.CellGrid;
import java.util.List;
import java.util.Map;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


public abstract class GridStyle {
  public static final int VIEW_WIDTH = 500;
  public static final int VIEW_HEIGHT = 500;

  public abstract void createGrid(int width, int height);

  public abstract void updateGrid(List<List<String>> listOfCells, Map<String, Color> stateToColor);

}
