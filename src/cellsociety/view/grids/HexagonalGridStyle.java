package cellsociety.view.grids;

import cellsociety.view.GridStyle;
import cellsociety.view.SimulationView;
import java.util.List;
import java.util.Map;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class HexagonalGridStyle extends GridStyle {

  public static final double HEX_ANGLE = Math.PI / 3;

  private Pane pane;
  private Polygon[][] grid;

  public HexagonalGridStyle(SimulationView currentSimulationView, Pane pane){
    super(currentSimulationView);
    this.pane = pane;
    pane.getStyleClass().add("hexagonal-gridpane");
  }

  private Polygon createHexagonalCell (double radius, int xPositionInGrid,
      int yPositionInGrid, double xPositionInPane, double yPositionInPane, Color color){
    Polygon cell = new Polygon();
    cell.getPoints().addAll(
        xPositionInPane, yPositionInPane - radius,
        xPositionInPane + radius * Math.cos(HEX_ANGLE), yPositionInPane - radius * Math.sin(HEX_ANGLE),
        xPositionInPane + radius * Math.cos(HEX_ANGLE), yPositionInPane + radius * Math.sin(HEX_ANGLE),
        xPositionInPane, yPositionInPane + radius,
        xPositionInPane - radius * Math.cos(HEX_ANGLE), yPositionInPane + radius * Math.sin(HEX_ANGLE),
        xPositionInPane - radius * Math.cos(HEX_ANGLE),
        yPositionInPane - radius * Math.sin(HEX_ANGLE));
    cell.setFill(color);
    cell.setOnMouseClicked(e -> handleClick(xPositionInGrid, yPositionInGrid));
    return cell;
  }

  @Override
  public Pane createGrid(int numCols, int numRows) {
    grid = new Polygon[numCols][numRows];
    double radius = calculateHexagonRadius(numCols, numRows);
    for (int rowNumber = 0; rowNumber < numRows; rowNumber++) {
      for (int colNumber = 0; colNumber < numCols; colNumber++) {
        double xPosition = calculateXPosition(colNumber, rowNumber, radius);
        double yPosition = calculateYPosition(rowNumber, radius);
        grid[colNumber][rowNumber] = createHexagonalCell(radius, rowNumber, colNumber, xPosition,
            yPosition, Color.WHITE);
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

  private double numberOfRadiusesVertically(int numCols) {
    if(numCols % 2 == 1){
      return (((numCols - 1) / 2) * 3) + 2;
    }
    else{
      return (numCols * 1.5) + Math.cos(HEX_ANGLE);
    }
  }

  private double numberOfRadiusesHorizontally(int numRows){
    return (numRows * 2) + 1;
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


  @Override
  public void updateGrid(List<List<String>> listOfStates, Map<String, Color> stateToColor) {
    for (int rowNumber = 0; rowNumber < listOfStates.size(); rowNumber++) {
      for (int columnNumber = 0; columnNumber < listOfStates.get(0).size(); columnNumber++) {
        grid[columnNumber][rowNumber].setFill(stateToColor.get(listOfStates.get(rowNumber).get(columnNumber)));
      }
    }
  }
}
