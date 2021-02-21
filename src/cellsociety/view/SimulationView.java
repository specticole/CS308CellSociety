package cellsociety.view;

import cellsociety.CellularAutomatonConfiguration;
import cellsociety.CellularAutomatonController;
import cellsociety.model.states.FireState;
import cellsociety.model.states.GameOfLifeState;
import cellsociety.model.states.PercolationState;
import cellsociety.model.states.SegregationState;
import cellsociety.model.states.WaTorWorldState;
import cellsociety.view.parameters.FireParameterBox;
import cellsociety.view.parameters.GameOfLifeParameterBox;
import cellsociety.view.parameters.ParameterBox;
import cellsociety.view.parameters.PercolationParameterBox;
import cellsociety.view.parameters.SegregationParameterBox;
import cellsociety.view.parameters.WaTorParameterBox;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Pair;

public class SimulationView {

  private GridPane masterLayout;
  private HBox titleBox;
  private GridStyle mainGrid;
  private Pane gridView;
  private VBox buttonBox;
  private Graph graph;
  private Button graphButton;
  private Button gridButton;
  private Button saveButton;
  private ParameterBox parameterBox;

  private CellularAutomatonController controller;
  private CellularAutomatonConfiguration config;
  private ResourceBundle bundle;

  private boolean graphShown;
  private boolean gridShown;

  public SimulationView(File configFile, ResourceBundle currentBundle){
    masterLayout = new GridPane();
    masterLayout.getStyleClass().add("simulation-gridpane");
    config = new CellularAutomatonConfiguration(configFile);
    bundle = currentBundle;
    controller = new CellularAutomatonController(this, configFile);
    graphShown = false;
    gridShown = true;
  }

  public Pair<CellularAutomatonController, GridPane> initialize(){
    createTitle();
    createButtons();
    parameterBox = createParameterBox();
    createGrid();

    masterLayout.add(titleBox, 0,0);
    masterLayout.add(buttonBox, 0,1);
    masterLayout.add(parameterBox.createFields(), 0,2);
    masterLayout.add(gridView, 1,0, 1,3);
    return new Pair<>(controller, masterLayout);
  }

  private void createButtons() {
    buttonBox = new VBox();
    buttonBox.getStyleClass().add("");

    graphButton = new Button(bundle.getString("ShowGraphButtonLabel"));
    graphButton.setOnAction(e -> toggleGraph());
    gridButton = new Button(bundle.getString("HideGridButtonLabel"));
    gridButton.setOnAction(e -> toggleGrid());
    saveButton = new Button(bundle.getString("SaveButtonLabel"));
    saveButton.setOnAction(e -> {
      try {
        saveXML();
      } catch (IOException ioException) {
        ioException.printStackTrace();
      }
    });

    buttonBox.getChildren().addAll(graphButton, gridButton, saveButton);
  }

  private void createGrid() {
    switch (config.getGridType()){
      case "rectangular":
        mainGrid = new RectangularGridStyle(new GridPane());
        break;
      case "hexagonal":
        mainGrid = new HexagonalGridStyle(new Pane());
        break;
    }
    gridView = mainGrid.createGrid(config.getGridHeight(), config.getGridWidth());
    mainGrid.updateGrid(config.getInitialStates(), config.getCellStyles());
  }

  private void createTitle() {
    titleBox = new HBox();
    titleBox.getStyleClass().add("title-box");
    Text titleText = new Text();
    titleText.setText(config.getSimulationMetadata().get("title"));
    titleText.getStyleClass().add("subtitle-text");
    titleBox.getChildren().add(titleText);
  }

  private void toggleGraph(){

  }

  private void toggleGrid() {
    if(gridShown){
      masterLayout.getChildren().remove(gridView);
      gridButton.setText(bundle.getString("ShowGridButtonLabel"));
      gridShown = false;
    }
    else{
      masterLayout.add(gridView, 1, 0, 1, 3);
      gridButton.setText(bundle.getString("HideGridButtonLabel"));
      gridShown = true;
    }
  }

  private void saveXML() throws IOException {
    controller.pauseSimulation();
    controller.saveConfigFile(masterLayout);
  }

  private ParameterBox createParameterBox() {
    switch (config.getSimulationType()) {
      case "gameoflife":
        return new GameOfLifeParameterBox(new VBox(), bundle, this);
      case "percolation":
        return new PercolationParameterBox(new VBox(), bundle, this);
      case "fire":
        return new FireParameterBox(new VBox(), bundle, this);
      case "wator":
        return new WaTorParameterBox(new VBox(), bundle, this);
      case "segregation":
        return new SegregationParameterBox(new VBox(), bundle, this);
      default:
        return null;
    }
  }

  public void updateView(List<List<String>> myStates){
    mainGrid.updateGrid(myStates, config.getCellStyles());
  }

  public void updateParameters(Map<String, String> parameterList) {
    controller.updateParameters(parameterList);
  }
}
