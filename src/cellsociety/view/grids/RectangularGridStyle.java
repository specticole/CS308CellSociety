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

  private Rectangle createRectangleCell(int rowNumber, int colNumber, double width,
      double height, Color color){
    Rectangle cell = new Rectangle(width, height);
    cell.setFill(color);
    cell.setOnMouseClicked(e -> handleClick(colNumber, rowNumber));
    return cell;
  }

  @Override
  public GridPane createGrid(int numCols, int numRows) {
    grid = new Rectangle[numCols][numRows];
    double rectangleWidth = calculateRectangleWidth(numCols);
    double rectangleHeight = calculateRectangleHeight(numRows);
    for (int rowNumber = 0; rowNumber < numRows; rowNumber++) {
      for (int colNumber = 0; colNumber < numCols; colNumber++) {
        grid[colNumber][rowNumber] = createRectangleCell(rowNumber, colNumber, rectangleWidth,
            rectangleHeight,
            Color.WHITE);
        pane.add(grid[colNumber][rowNumber], colNumber, rowNumber);
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
    for (int rowNumber = 0; rowNumber < listOfStates.size(); rowNumber++) {
      for (int columnNumber = 0; columnNumber < listOfStates.get(0).size(); columnNumber++) {
        grid[columnNumber][rowNumber].setFill(stateToColor.get(listOfStates.get(rowNumber).get(columnNumber)));
      }
    }
  }
}
