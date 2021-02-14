package cellsociety.view;

import cellsociety.CellularAutomatonConfiguration;
import cellsociety.CellularAutomatonController;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;


public class CellularAutomatonView {

  @FXML
  private GridPane masterLayout;
  @FXML
  private GridPane mainGrid;
  @FXML
  private Button startResetButton;
  @FXML
  private Button pauseResumeButton;
  @FXML
  private Slider speedSlider;

  private boolean started;
  private boolean paused;

  ResourceBundle bundle;
  CellularAutomatonController controller;

  public void initialize(){
    Locale locale = new Locale("en", "US");
    bundle = ResourceBundle.getBundle("labels", locale);

    controller = new CellularAutomatonController();
    CellularAutomatonConfiguration config = controller.loadConfigFile("ControllerTest.xml");
    RectangularGridStyle grid = new RectangularGridStyle(mainGrid);
    grid.createGrid(config.getGridHeight(),config.getGridWidth());
    Map<String, Color> dummyMap = new HashMap<>();
    dummyMap.put("A", Color.BLACK);
    dummyMap.put("D", Color.NAVAJOWHITE);
    grid.updateGrid(config.getInitialStates(), dummyMap);

    started = false;
    paused = true;
  }

  private void updateButtonLabels(){
    if(started){
      startResetButton.setText(bundle.getString("ResetButtonLabel"));
    }
    else{
      startResetButton.setText(bundle.getString("StartButtonLabel"));
    }
    if(paused){
      pauseResumeButton.setText(bundle.getString("ResumeButtonLabel"));
    }
    else{
      pauseResumeButton.setText(bundle.getString("PauseButtonLabel"));
    }
  }

  public void startResetButtonClick() {
    if(started){
      controller.resetSimulation();
      started = false;
      paused = true;
    }
    else{
      controller.playSimulation();
      started = true;
      paused = false;
    }
    updateButtonLabels();
  }

  public void pauseResumeButtonClick() {
    if(paused){
      controller.pauseSimulation();
      started = true;
      paused = false;
    }
    else{
      controller.playSimulation();
      paused = true;
    }
    updateButtonLabels();
  }

  public void stepButtonClick() {
    controller.stepOnce();
    started = true;
    paused = true;
    updateButtonLabels();
  }

  public void speedButtonClick() {
    controller.changeRateSlider((int) speedSlider.getValue());
  }
}
