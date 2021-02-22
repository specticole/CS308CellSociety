package cellsociety.view;

import cellsociety.CellularAutomatonConfiguration;
import cellsociety.CellularAutomatonController;
import cellsociety.view.grids.HexagonalGridStyle;
import cellsociety.view.grids.RectangularGridStyle;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Pair;

public class SimulationView {

  private GridPane masterLayout;
  private GridStyle mainGrid;
  private Pane gridView;
  private Graph graph;
  private LineChart graphView;
  private Button graphButton;
  private Button gridButton;
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
    HBox titleBox = createTitle();
    VBox buttonBox = createButtons();
    parameterBox = createParameterBox();
    createGrid();
    createGraph();

    masterLayout.add(titleBox, 0,0);
    masterLayout.add(buttonBox, 0,1);
    masterLayout.add(parameterBox.initialize(), 0,2);
    masterLayout.add(gridView, 1,0, 1,3);
    return new Pair<>(controller, masterLayout);
  }

  private VBox createButtons() {
    VBox buttonBox = new VBox();

    graphButton = new Button(bundle.getString("ShowGraphButtonLabel"));
    graphButton.setOnAction(e -> toggleGraph());
    gridButton = new Button(bundle.getString("HideGridButtonLabel"));
    gridButton.setOnAction(e -> toggleGrid());
    Button saveButton = new Button(bundle.getString("SaveButtonLabel"));
    saveButton.setOnAction(e -> saveXML());
    Button deleteSimulationButton = new Button(bundle.getString("DeleteSimulationButtonLabel"));
    deleteSimulationButton.setOnAction(e -> deleteSimulation());

    buttonBox.getChildren().addAll(graphButton, gridButton, saveButton, deleteSimulationButton);
    return buttonBox;
  }

  private void deleteSimulation() {
    ((GridPane) masterLayout.getParent()).getChildren().remove(masterLayout);
  }

  private void createGrid() {
    switch (config.getGridType()){
      case "rectangular":
        mainGrid = new RectangularGridStyle(this, new GridPane());
        break;
      case "hexagonal":
        mainGrid = new HexagonalGridStyle(this, new Pane());
        break;
    }
    gridView = mainGrid.createGrid(config.getGridWidth(), config.getGridHeight());
    mainGrid.updateGrid(config.getInitialStates(), config.getCellStyles());
  }

  private void createGraph(){
    graph = new Graph(config);
    graphView = graph.initialize();
  }

  private HBox createTitle() {
    HBox titleBox = new HBox();
    titleBox.getStyleClass().add("title-box");
    Text titleText = new Text();
    titleText.setText(config.getSimulationMetadata().get("title"));
    titleText.getStyleClass().add("subtitle-text");
    titleBox.getChildren().add(titleText);
    return titleBox;
  }

  private void toggleGraph(){
    if(graphShown){
      masterLayout.getChildren().remove(graphView);
      graphButton.setText(bundle.getString("ShowGraphButtonLabel"));
      graphShown = false;
    }
    else{
      masterLayout.add(graphView, 2, 0, 1, 3);
      graphButton.setText(bundle.getString("HideGraphButtonLabel"));
      graphShown = true;
    }
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

  private void saveXML(){
    controller.pauseSimulation();
    controller.saveConfigFile(masterLayout);
  }

  private ParameterBox createParameterBox() {
    return new ParameterBox(new VBox(), bundle, this,
        config.getSimulationParameters(), config.getCellStyles().keySet());
  }

  public void changeCell(int columnNumber, int rowNumber){
    controller.changeCell(parameterBox.getState(), columnNumber, rowNumber);
  }

  public void updateView(List<List<String>> myStates){
    mainGrid.updateGrid(myStates, config.getCellStyles());
    graph.updateGraph(myStates);
  }

  public void updateParameters(Map<String, String> parameterList) {
    controller.updateParameters(parameterList);
  }

}
