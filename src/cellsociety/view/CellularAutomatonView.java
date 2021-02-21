package cellsociety.view;

import cellsociety.CellularAutomatonConfiguration;
import cellsociety.CellularAutomatonController;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


public class CellularAutomatonView {

  private GridPane masterLayout;
  private Pane mainGrid;
  @FXML
  private Text title;
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
  GridStyle grid;

  public CellularAutomatonView(GridPane gridPane){
    masterLayout = gridPane;
    masterLayout.getStyleClass().add("master-gridpane");
  }

  public GridPane initialize(ResourceBundle bundle){

    controller = new CellularAutomatonController(this);
    CellularAutomatonConfiguration config = controller.loadConfigFile();
    controller.initializeForConfig(config);

    createGrid(config);

    started = false;
    paused = true;

    return masterLayout;
  }


  private void createGrid(CellularAutomatonConfiguration config){
    switch (config.getGridType()) {
      case "rectangular":
        grid = new RectangularGridStyle();
        mainGrid = grid.createGrid(config.getGridWidth(), config.getGridHeight());
    }
    masterLayout.add(mainGrid, 0, 0,4,1);
  }

  public void createButtons(){

  }

  public void updateXML(CellularAutomatonConfiguration config){
    controller.initializeForConfig(config);
    title.setText(config.getSimulationMetadata().get("title"));
    mainGrid.getChildren().clear();
    //grid = new RectangularGridStyle(mainGrid);
    cellStyles = config.getCellStyles();
    //grid.createGrid(config.getGridHeight(),config.getGridWidth());
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
    started = true;
    paused = false;
    updateButtonLabels();
  }

  public void loadFileClick() {
    CellularAutomatonConfiguration config = controller.loadConfigFile(masterLayout);
    if(config != null){
      updateXML(config);
      controller.pauseSimulation();
      started = false;
      paused = true;
      updateButtonLabels();
    }
  }

  public void updateView(List<List<String>> myStates){
    grid.updateGrid(myStates, cellStyles);
  }
}
