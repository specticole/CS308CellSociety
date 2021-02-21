package cellsociety.view;

import java.util.List;
import java.util.Map;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class HexagonalGridStyle extends GridStyle{

  public static final double HEX_ANGLE = Math.PI / 3;

  private Pane pane;
  private Polygon[][] grid;

  public HexagonalGridStyle(Pane pane){
    this.pane = pane;
    pane.getStyleClass().add("hexagonal-gridpane");
  }

  private void createHexagonalCell (double radius, double xPos, double yPos, Color color){
    Polygon cell = new Polygon();
    cell.getPoints().addAll(new Double[]{
        xPos, yPos - radius,
        xPos + radius * Math.cos(HEX_ANGLE), yPos - radius * Math.sin(HEX_ANGLE)
    });
  }

  @Override
  public Pane createGrid(int width, int height) {
    return null;
  }

  @Override
  public void updateGrid(List<List<String>> listOfCells, Map<String, Color> stateToColor) {

  }
}
