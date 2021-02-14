package cellsociety.view;

import cellsociety.CellularAutomatonConfiguration;
import cellsociety.CellularAutomatonController;
import cellsociety.xml.XMLException;
import java.util.HashMap;
import java.util.List;
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
  Map<String, Color> cellStyles;
  RectangularGridStyle grid;

  public void initialize(){
    Locale locale = new Locale("en", "US");
    bundle = ResourceBundle.getBundle("labels", locale);

    controller = new CellularAutomatonController(this);
    CellularAutomatonConfiguration config = new CellularAutomatonConfiguration("ControllerTest"
        + ".xml");
    updateXML(config);

    started = false;
    paused = true;
  }

  public void updateXML(CellularAutomatonConfiguration config){
    mainGrid.getChildren().clear();
    grid = new RectangularGridStyle(mainGrid);
    cellStyles = config.getCellStyles();
    grid.createGrid(config.getGridHeight(),config.getGridWidth());
    grid.updateGrid(config.getInitialStates(), cellStyles);
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
      controller.playSimulation();
      started = true;
      paused = false;
    }
    else{
      controller.pauseSimulation();
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

  public void loadFileClick() {
    CellularAutomatonConfiguration config = controller.loadConfigFile(masterLayout);
    if(config != null){
      updateXML(config);
    }
  }

  public void updateView(List<List<String>> myStates){
    grid.updateGrid(myStates, cellStyles);
  }
}
