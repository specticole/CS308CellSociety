package cellsociety.view.grids;

import cellsociety.view.GridStyle;
import cellsociety.view.SimulationView;
import java.util.List;
import java.util.Map;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 * Creates a hexagonal grid for the View to display.
 *
 * Handles the calculation of the size of each hexagon as well as its cell position.
 * Hexagons are only regular, they cannot be stretched to fit the display.
 * This means that there will be excess space on one of the axis.
 * Each cell is stored in a 2D array which represents its position.
 * The position is needed to update the state of a cell from the model.
 *
 * @author Bill Guo
 */

public class HexagonalGridStyle extends GridStyle {

  public static final double HEX_ANGLE = Math.PI / 3;

  private Pane pane;
  private Polygon[][] grid;

  /**
   * Initializes a hexagonal GridStyle.
   * Requires the SimulationView that is making this object, as well as a provided pane.
   * Parent SimulationView is required to call a function from the parent when a cell is clicked.
   *
   * @param currentSimulationView Parent SimulationView
   * @param pane
   */
  public HexagonalGridStyle(SimulationView currentSimulationView, Pane pane){
    super(currentSimulationView);
    this.pane = pane;
    pane.getStyleClass().add("hexagonal-gridpane");
  }

  private Polygon createHexagonalCell (double radius, int xPositionInGrid,
      int yPositionInGrid, double xPositionInPane, double yPositionInPane, Color color,
      boolean outlines){
    Polygon cell = new Polygon();
    cell.getPoints().addAll(
        xPositionInPane, yPositionInPane - radius,
        xPositionInPane+radius * Math.sin(HEX_ANGLE), yPositionInPane-radius * Math.cos(HEX_ANGLE),
        xPositionInPane+radius * Math.sin(HEX_ANGLE), yPositionInPane+radius * Math.cos(HEX_ANGLE),
        xPositionInPane, yPositionInPane + radius,
        xPositionInPane-radius * Math.sin(HEX_ANGLE), yPositionInPane+radius * Math.cos(HEX_ANGLE),
        xPositionInPane-radius * Math.sin(HEX_ANGLE), yPositionInPane-radius * Math.cos(HEX_ANGLE));
    cell.setFill(color);
    if(outlines){
      cell.setStroke(Color.BLACK);
    }
    cell.setOnMouseClicked(e -> handleClick(xPositionInGrid, yPositionInGrid));
    return cell;
  }

  /**
   * Creates the Pane object that is added to the SimulationView.
   * Used by the SimulationView to show the grid on the UI.
   *
   * @param numCols number of columns specified by the config file
   * @param numRows number of rows specified by the config file
   * @return Pane object to be added to the SimulationView main GridPane
   */

  @Override
  public Pane createGrid(int numCols, int numRows, boolean outlines) {
    grid = new Polygon[numCols][numRows];
    double radius = calculateHexagonRadius(numCols, numRows);
    System.out.println(radius);
    for (int rowNumber = 0; rowNumber < numRows; rowNumber++) {
      for (int colNumber = 0; colNumber < numCols; colNumber++) {
        double xPosition = calculateXPosition(colNumber, rowNumber, radius);
        System.out.println("x: " + xPosition);
        double yPosition = calculateYPosition(rowNumber, radius);
        System.out.println("y: " + yPosition);
        grid[colNumber][rowNumber] = createHexagonalCell(radius, rowNumber, colNumber, xPosition,
            yPosition, Color.WHITE, outlines);
        pane.getChildren().add(grid[colNumber][rowNumber]);
      }
    }
    return pane;
  }

  private double calculateHexagonRadius(int numCols, int numRows){
    if(numberOfRadiusesVertically(numCols) > numberOfRadiusesHorizontally(numRows)){
      return VIEW_HEIGHT / numberOfRadiusesVertically(numCols);
    }
    else{
      return VIEW_WIDTH / numberOfRadiusesHorizontally(numRows);
    }

  }

  private double numberOfRadiusesVertically(int numRows) {
    if(numRows % 2 == 1){
      return (((numRows - 1) / 2) * 3) + 2;
    }
    else{
      return (numRows * 1.5) + Math.cos(HEX_ANGLE);
    }
  }

  private double numberOfRadiusesHorizontally(int numCols){
    return ((numCols * 2) + 1) * Math.sin(HEX_ANGLE);
  }

  private double calculateXPosition(int colNumber, int rowNumber, double radius){
    if (rowNumber % 2 == 0){
      return ((colNumber * 2) + 1) * radius * Math.sin(HEX_ANGLE);
    }
    else{
      return ((colNumber * 2) + 2) * radius * Math.sin(HEX_ANGLE);
    }
  }

  private double calculateYPosition(int rowNumber, double radius){
    return ((rowNumber * 1.5) + 1) * radius;
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
