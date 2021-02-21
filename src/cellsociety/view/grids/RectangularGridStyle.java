package cellsociety.view.grids;

import cellsociety.view.GridStyle;
import cellsociety.view.SimulationView;
import java.awt.Point;
import java.util.List;
import java.util.Map;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class RectangularGridStyle extends GridStyle {

  public static final double SPACING = 1;

  private GridPane pane;
  private Rectangle[][] grid;

  public RectangularGridStyle(SimulationView currentSimulationView, GridPane gridPane){
    super(currentSimulationView);
    pane = gridPane;
    pane.getStyleClass().add("rectangular-gridpane");
  }

  private Rectangle createRectangleCell(int rowNumber, int columnNumber, double width,
      double height, Color color){
    Rectangle cell = new Rectangle(width, height);
    cell.setFill(color);
    cell.setOnMouseClicked(e -> handleClick(columnNumber, rowNumber));
    return cell;
  }

  @Override
  public GridPane createGrid(int numRows, int numCols) {
    pane.getStyleClass().add("my-grid-pane");
    grid = new Rectangle[numRows][numCols];
    double rectangleWidth = calculateRectangleWidth(numCols);
    double rectangleHeight = calculateRectangleHeight(numRows);
    for (int rowNumber = 0; rowNumber < numRows; rowNumber++) {
      for (int colNumber = 0; colNumber < numCols; colNumber++) {
        grid[rowNumber][colNumber] = createRectangleCell(rowNumber, colNumber, rectangleWidth,
            rectangleHeight,
            Color.WHITE);
        pane.add(grid[rowNumber][colNumber], colNumber, rowNumber);
      }
    }
    return pane;
  }

  private double calculateRectangleWidth(int numCols){
    int numberOfGaps = numCols - 1;
    double totalWidth = VIEW_WIDTH - numberOfGaps * (SPACING);
    return totalWidth / numCols;
  }

  private double calculateRectangleHeight(int numRows){
    int numberOfGaps = numRows - 1;
    double totalHeight = VIEW_HEIGHT - numberOfGaps * (SPACING);
    return totalHeight / numRows;
  }

  @Override
  public void updateGrid(List<List<String>> listOfStates, Map<String, Color> stateToColor) {
    for (int i = 0; i < listOfStates.size(); i++) {
      for (int j = 0; j < listOfStates.get(0).size(); j++) {
        grid[i][j].setFill(stateToColor.get(listOfStates.get(i).get(j)));
      }
    }
  }
}
