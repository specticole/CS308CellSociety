package cellsociety.view;

import cellsociety.CellGrid;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class RectangularGridStyle extends GridStyle{

  Pane pane;
  Rectangle[][] grid;

  public RectangularGridStyle(Pane pane){
    this.pane = pane;
  }

  public Rectangle createRectangleCell(double width, double height, double xPos, double yPos, Color color){
    Rectangle cell = new Rectangle(width, height);
    cell.relocate(xPos, yPos);
    cell.setFill(color);
    return cell;

  }

  @Override
  public void createGrid(int numRows, int numCols) {
    grid = new Rectangle[numRows][numCols];
    int rectangleWidth = GridStyle.VIEW_WIDTH / numCols;
    int rectangleHeight = GridStyle.VIEW_HEIGHT / numRows;
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        grid[i][j] = createRectangleCell(rectangleWidth, rectangleHeight, i * rectangleWidth, j * rectangleHeight, Color.ORANGE);
        pane.getChildren().add(grid[i][j]);
      }
    }
  }

  @Override
  public void updateGrid(CellGrid cellGrid, int time) {
    
  }
}
