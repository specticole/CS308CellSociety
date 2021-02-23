package cellsociety.view;

import cellsociety.controller.CellularAutomatonController;
import cellsociety.xml.XMLException;
import java.io.File;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Pair;


public class CellularAutomatonView {

  public static final String LARGEFONT_CSS = "cellsociety/resources/largefont.css";
  public static final String NORMALFONT_CSS = "cellsociety/resources/normalfont.css";

  private GridPane masterLayout;
  private Button startResetButton;
  private Button pauseResumeButton;
  private Button stepButton;
  private Slider speedSlider;
  private Button applySpeedButton;
  private Button fontButton;
  private Button colorButton;

  private boolean started;
  private boolean paused;
  private boolean largefont;
  private boolean dark;

  private int newRowIndex;

  ResourceBundle bundle;
  ArrayList<CellularAutomatonController> simulationControllers;


  public CellularAutomatonView(GridPane gridPane, ResourceBundle resourceBundle){
    masterLayout = gridPane;
    masterLayout.getStyleClass().add("master-gridpane");
    bundle = resourceBundle;
    simulationControllers = new ArrayList<>();

    largefont = false;
    dark = false;
    started = false;
    paused = true;
    newRowIndex = 2;
  }

  public GridPane initialize(){
    createTitle();
    createSimulationControlButtons();
    createNewSimulationButton(newRowIndex);
    incrementRowIndex();

    return masterLayout;
  }

  private void incrementRowIndex() {
    newRowIndex += 2;
  }

  private void createTitle() {
    HBox titleBox = new HBox();
    titleBox.getStyleClass().add("title-box");
    Text titleText = new Text();
    titleText.setText(bundle.getString("Title"));
    titleText.getStyleClass().add("title-text");

    titleBox.getChildren().add(titleText);
    masterLayout.add(titleBox,0,0, 3,1);
  }

  private void createSimulationControlButtons() {
    HBox controlsBox = new HBox();
    controlsBox.getStyleClass().add("controls-box");

    startResetButton = new Button(bundle.getString("StartButtonLabel"));
    startResetButton.setOnAction(e -> startResetButtonClick());
    startResetButton.getStyleClass().add("button-text");
    pauseResumeButton = new Button(bundle.getString("ResumeButtonLabel"));
    pauseResumeButton.setOnAction(e -> pauseResumeButtonClick());
    pauseResumeButton.getStyleClass().add("button-text");
    stepButton = new Button(bundle.getString("StepButtonLabel"));
    stepButton.setOnAction(e -> stepButtonClick());
    stepButton.getStyleClass().add("button-text");
    fontButton = new Button(bundle.getString("IncreaseFontButtonLabel"));
    fontButton.setOnAction(e -> fontButtonClick());
    fontButton.getStyleClass().add("button-text");
    colorButton = new Button(bundle.getString("DarkButtonLabel"));
    colorButton.setOnAction(e -> colorButtonClick());
    colorButton.getStyleClass().add("button-text");

    Text speedText = new Text();
    speedText.setText(bundle.getString("SpeedLabel"));
    createSpeedSlider();
    applySpeedButton = new Button(bundle.getString("ApplyButtonLabel"));
    applySpeedButton.setOnAction(e -> speedButtonClick());
    applySpeedButton.getStyleClass().add("button-text");

    controlsBox.getChildren().addAll(startResetButton, pauseResumeButton, stepButton, speedText,
        speedSlider, applySpeedButton, fontButton, colorButton);
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
    Button newSimulationButton = new Button(bundle.getString("NewSimulationButtonLabel"));
    newSimulationButton.setOnAction(e -> handleLoadNewSimulation(newSimulationButtonBox));
    newSimulationButton.getStyleClass().add("button-text");
    newSimulationButtonBox.getChildren().add(newSimulationButton);
    masterLayout.add(newSimulationButtonBox, 0,rowIndex);

  }
  private void handleLoadNewSimulation(HBox newSimulationButtonBox) {
    try {
      File configFile = loadConfigFile();
      SimulationView simulationView = new SimulationView(configFile, bundle, this);
      masterLayout.getChildren().remove(newSimulationButtonBox);
      Pair<CellularAutomatonController, GridPane> simulationPair = simulationView.initialize();
      masterLayout.add(simulationPair.getValue(), 0, newRowIndex - 2, 3,2);
      simulationControllers.add(simulationPair.getKey());
      createNewSimulationButton(newRowIndex);
      incrementRowIndex();

      for (CellularAutomatonController controller: simulationControllers) {
        controller.pauseSimulation();
      }
      started = false;
      paused = true;
      updateButtonLabels();
    }
    catch (XMLException e) {
      if (e.getMessage() != null) {
        makeAlert(e.getMessage());
      }
      else {
        makeAlert("Invalid XML file");
      }
    }

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
    if(largefont){
      fontButton.setText(bundle.getString("DecreaseFontButtonLabel"));
    }
    else{
      fontButton.setText(bundle.getString("IncreaseFontButtonLabel"));
    }
    if(dark){
      colorButton.setText(bundle.getString("LightButtonLabel"));
    }
    else{
      colorButton.setText(bundle.getString("DarkButtonLabel"));
    }
  }

  private void startResetButtonClick() {
    if(started){
      for (CellularAutomatonController controller: simulationControllers) {
        controller.resetSimulation();
      }
      started = false;
      paused = true;
    }
    else{
      for (CellularAutomatonController controller: simulationControllers) {
        controller.playSimulation();
      }
      started = true;
      paused = false;
    }
    updateButtonLabels();
  }

  /**
   * Used by SimulationView to pause all simulations when one is saved
   */
  public void pauseAllSims(){
    for (CellularAutomatonController controller: simulationControllers) {
      controller.pauseSimulation();
    }
    paused = true;
    updateButtonLabels();
  }

  private void pauseResumeButtonClick() {
    if(paused){
      for (CellularAutomatonController controller: simulationControllers) {
        controller.playSimulation();
      }
      started = true;
      paused = false;
    }
    else {
      pauseAllSims();
    }
  }

  private void stepButtonClick() {
    for (CellularAutomatonController controller: simulationControllers) {
      controller.stepOnce();
    }
    started = true;
    paused = true;
    updateButtonLabels();
  }

  private void speedButtonClick() {
    for (CellularAutomatonController controller: simulationControllers) {
      controller.changeRateSlider((int) speedSlider.getValue());
    }
    started = true;
    paused = false;
    updateButtonLabels();
  }

  private void fontButtonClick() {
    masterLayout.getScene().getStylesheets().clear();
    if(largefont){
      masterLayout.getScene().getStylesheets().add(NORMALFONT_CSS);
      largefont = false;
    }
    else{
      masterLayout.getScene().getStylesheets().add(LARGEFONT_CSS);
      largefont = true;
    }
    updateButtonLabels();
  }

  private void colorButtonClick() {
    if(dark){
      masterLayout.setStyle("-fx-background-color: #CCCCCC");
      dark = false;
    }
    else{
      masterLayout.setStyle("-fx-background-color: #696969");
      dark = true;
    }
    updateButtonLabels();
  }

  private File loadConfigFile() throws XMLException {
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(new ExtensionFilter("XML Document", "*.xml"));
    File configFile = fileChooser.showOpenDialog(masterLayout.getScene().getWindow());
    if (configFile == null) {
      throw new XMLException(new IllegalArgumentException(), "No file selected");
    }
    return configFile;
  }

  /**
   * Displays alert message, used to handle errors that occur
   * during the loading or parsing of a configuration file
   *
   * @param errorMessage - the message displayed to the user
   */
  public void makeAlert(String errorMessage) {
    Alert alert = new Alert(AlertType.ERROR, errorMessage);
    alert.showAndWait().filter(response -> response == ButtonType.OK);
  }

}
