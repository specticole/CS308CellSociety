package cellsociety.view;

import cellsociety.CellularAutomatonController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;


public class CellularAutomatonView {

  @FXML
  private GridPane masterLayout;
  @FXML
  private GridPane mainGrid;
  @FXML
  private Button startResetButton;


  CellularAutomatonController controller;

  public void initialize(){
    controller = new CellularAutomatonController("initial/xml");
    RectangularGridStyle grid = new RectangularGridStyle(mainGrid);
    grid.createGrid(100,100);
  }


  public void startButtonClick() {
    controller.playSimulation();

  }

  public void pauseButtonClick() {
    controller.pauseSimulation();
  }
}
