package cellsociety.view;

import cellsociety.CellularAutomatonConfiguration;
import cellsociety.CellularAutomatonController;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


public class CellularAutomatonView {

  private GridPane masterLayout;
  private Button startResetButton;
  private Button pauseResumeButton;
  private Button stepButton;
  private Slider speedSlider;
  private Button applySpeedButton;
  private Button newSimulationButton;

  private boolean started;
  private boolean paused;
  private int newRowIndex;

  ResourceBundle bundle;
  CellularAutomatonController controller;
  Map<String, Color> cellStyles;
  GridStyle grid;

  public CellularAutomatonView(GridPane gridPane){
    masterLayout = gridPane;
    masterLayout.getStyleClass().add("master-gridpane");
  }

  public GridPane initialize(ResourceBundle resourceBundle){
    controller = new CellularAutomatonController(this);
    bundle = resourceBundle;
    started = false;
    paused = true;
    newRowIndex = 2;

    createTitle();
    createSimulationControlButtons();
    createNewSimulationButton(newRowIndex);
    newRowIndex++;

    return masterLayout;
  }

  private void createTitle() {
    HBox titleBox = new HBox();
    titleBox.getStyleClass().add("title-box");
    Text titleText = new Text();
    titleText.setText(bundle.getString("Title"));
    titleText.getStyleClass().add("text-style");

    titleBox.getChildren().add(titleText);
    masterLayout.add(titleBox,0,0, 3,1);
  }

  private void createSimulationControlButtons() {
    HBox controlsBox = new HBox();
    controlsBox.getStyleClass().add("controls-box");

    startResetButton = new Button(bundle.getString("StartButtonLabel"));
    startResetButton.setOnAction(e -> startResetButtonClick());
    pauseResumeButton = new Button(bundle.getString("ResumeButtonLabel"));
    pauseResumeButton.setOnAction(e -> pauseResumeButtonClick());
    stepButton = new Button(bundle.getString("StepButtonLabel"));
    stepButton.setOnAction(e -> stepButtonClick());

    Text speedText = new Text();
    speedText.setText(bundle.getString("SpeedLabel"));
    createSpeedSlider();
    applySpeedButton = new Button(bundle.getString("ApplySpeedButtonLabel"));
    applySpeedButton.setOnAction(e -> speedButtonClick());


    controlsBox.getChildren().addAll(startResetButton, pauseResumeButton, stepButton, speedText,
        speedSlider, applySpeedButton);
    masterLayout.add(controlsBox,0,1, 3,1);
  }

  private void createSpeedSlider(){
    speedSlider = new Slider();
    speedSlider.adjustValue(3);
    speedSlider.setMin(1);
    speedSlider.setMax(5);
    speedSlider.getStyleClass().add("speed-slider");
  }

  private void createNewSimulationButton(int rowIndex){
    HBox newSimulationButtonBox = new HBox();
    newSimulationButtonBox.getStyleClass().add("button-box");
    newSimulationButton = new Button(bundle.getString("NewSimulationButtonLabel"));
    newSimulationButton.setOnAction(e -> loadFileClick());
    newSimulationButtonBox.getChildren().add(newSimulationButton);
    masterLayout.add(newSimulationButtonBox, 0,rowIndex);

  }

  public void loadFileClick() {
    CellularAutomatonConfiguration config = controller.loadConfigFile(masterLayout);
    SimulationView simulationView = new SimulationView(config);
    newSimulationButton.setVisible(false);
    newSimulationButton.setDisable(true);
    masterLayout.add(simulationView.initialize(), 0, newRowIndex - 1, 3,2);
    createNewSimulationButton(newRowIndex);
    newRowIndex++;

    controller.pauseSimulation();
    started = false;
    paused = true;
    updateButtonLabels();
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

  public void updateView(List<List<String>> myStates){
    grid.updateGrid(myStates, cellStyles);
  }
}
