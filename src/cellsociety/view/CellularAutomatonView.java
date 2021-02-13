package cellsociety.view;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;


public class CellularAutomatonView {

  @FXML
  private GridPane masterLayout;
  @FXML
  private GridPane mainGrid;


  public void initialize(){
    RectangularGridStyle grid = new RectangularGridStyle(mainGrid);
    grid.createGrid(100,100);
  }
}
