package cellsociety.view;

import cellsociety.CellGrid;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class RectangularGridStyle extends GridStyle{

  public static final double SPACING = 1;

  GridPane pane;
  Rectangle[][] grid;

  public RectangularGridStyle(Parent pane){
    this.pane = (GridPane) pane;
  }

  public Rectangle createRectangleCell(double width, double height, Color color){
    Rectangle cell = new Rectangle(width, height);
    cell.setFill(color);
    return cell;
  }

  @Override
  public void createGrid(int numRows, int numCols) {
    grid = new Rectangle[numRows][numCols];
    double rectangleWidth = calculateRectangleWidth(numCols);
    double rectangleHeight = calculateRectangleHeight(numRows);
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        grid[i][j] = createRectangleCell(rectangleWidth, rectangleHeight, Color.ORANGE);
        pane.add(grid[i][j], j, i);
      }
    }
    pane.getStyleClass().add("my-grid-pane");
    pane.setAlignment(Pos.TOP_CENTER);
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
  public void updateGrid(CellGrid cellGrid, int time) {

  }
}
