package cellsociety.view;

import java.util.List;
import java.util.Map;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


public abstract class GridStyle {
  public static final int VIEW_WIDTH = 250;
  public static final int VIEW_HEIGHT = 250;

  protected SimulationView simulationView;

  public GridStyle(SimulationView currentSimulationView){
    simulationView = currentSimulationView;
  }

  public abstract Pane createGrid(int numCols, int numRows);

  public abstract void updateGrid(List<List<String>> listOfCells, Map<String, Color> stateToColor);

  public void handleClick(int colNumber, int rowNumber){
    simulationView.changeCell(colNumber, rowNumber);
  }

}
