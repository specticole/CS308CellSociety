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

/**
 * Creates a rectangular grid for the SimulationView to display.
 *
 * Rectangular grids use a GridPane object to handle position of the cell automatically.
 * Handles the math behind calculating the size of each cell based on the grid dimensions.
 * Also takes into account the spacing of the GridPane when calculating rectangle dimensions.
 *
 * @author Bill Guo
 */

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

  /**
   * Creates the Pane object that is added to the SimulationView.
   * Used by the SimulationView to show the grid on the UI.
   *
   * @param numCols number of columns specified by the config file
   * @param numRows number of rows specified by the config file
   * @param outlines whether or not outlines are drawn around the rectangle in createRectangleCell
   * @return Pane object to be added to the SimulationView main GridPane
   */
  @Override
  public GridPane createGrid(int numCols, int numRows, boolean outlines) {
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
    System.out.println(outlines);
    if (!outlines) {
      pane.getStyleClass().clear();
      pane.getStyleClass().add("rectangular-gridpane-nospace");
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


  /**
   * Updates the grid to show the next set of states.
   * Used by SimulationView to send the information from the controller to the grid.
   *
   * @param listOfStates 2D list of the new states from the controller
   * @param stateToColor Map that converts a state to a specific color
   */
  @Override
  public void updateGrid(List<List<String>> listOfStates, Map<String, Color> stateToColor) {
    for (int rowNumber = 0; rowNumber < listOfStates.size(); rowNumber++) {
      for (int columnNumber = 0; columnNumber < listOfStates.get(0).size(); columnNumber++) {
        grid[columnNumber][rowNumber].setFill(stateToColor.get(listOfStates.get(rowNumber).get(columnNumber)));
      }
    }
  }
}
