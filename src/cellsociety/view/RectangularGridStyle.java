package cellsociety.view;

import cellsociety.CellGrid;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class RectangularGridStyle extends GridStyle{

  Pane pane;
  Rectangle[][] grid;

  public Rectangle createRectangleCell(double width, double height, double xPos, double yPos, Color color){
    Rectangle cell = new Rectangle(width, height);
    return cell;
  }

  @Override
  public void createGrid(int width, int height) {

  }

  @Override
  public void updateGrid(CellGrid cellGrid, int time) {

  }
}
