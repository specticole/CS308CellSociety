package cellsociety.view;

import cellsociety.controller.CellularAutomatonConfiguration;
import cellsociety.controller.CellularAutomatonController;
import cellsociety.controller.CellularAutomatonStyle;
import cellsociety.view.grids.HexagonalGridStyle;
import cellsociety.view.grids.RectangularGridStyle;
import cellsociety.xml.XMLException;
import java.io.File;
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


/**
 * The controls and display the all correspond to one created simulation.
 * Each simulation has a display of the grid of the simulation, a graph that represents the
 * counts of each state in the simulation, a control panel to hide the aforementioned views,
 * save/delete the current state, and change the parameters of the simulation.
 *
 * @author Bill Guo
 */


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
  private CellularAutomatonStyle style;
  private CellularAutomatonView parentView;
  private ResourceBundle bundle;

  private boolean graphShown;
  private boolean gridShown;

  /**
   * Constructor of a SimulationView.
   * Each simulation has a controller that communicates information to and from the model.
   *
   * @param configFile XML file to be read by the parser to create the CellularAut
   * @param currentBundle language of the labels
   * @param parent parent CellularAutomatonView to pause all simulations from one
   * @param styleParameters the style settings of this simulation
   */
  public SimulationView(File configFile, ResourceBundle currentBundle,
      CellularAutomatonView parent, CellularAutomatonStyle styleParameters){
    masterLayout = new GridPane();
    masterLayout.getStyleClass().add("simulation-gridpane");
    config = new CellularAutomatonConfiguration(configFile);
    bundle = currentBundle;
    controller = new CellularAutomatonController(this, configFile);
    style = styleParameters;
    parentView = parent;

    graphShown = false;
    gridShown = true;
  }

  /**
   * Handles the creation of the JavaFX UI elements for each simulation, such as the title,
   * buttons, parameter changing, grid, and the graph.
   * @return a pair that contains the controller and the corresponding view so that the parent
   * CellularAutomatonView can control and display the simulation
   */
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
    if(style != null){
      gridView = mainGrid.createGrid(config.getGridWidth(), config.getGridHeight(),
          style.getCellOutlines());
    }
    else {
      gridView = mainGrid.createGrid(config.getGridWidth(), config.getGridHeight(), true);
    }
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
    parentView.pauseAllSims();
    try {
      controller.saveConfigFile(masterLayout);
    }
    catch (XMLException e) {
      parentView.makeAlert("No file selected");
    }
  }

  private ParameterBox createParameterBox() {
    return new ParameterBox(new VBox(), bundle, this,
        config.getSimulationParameters(), config.getCellStyles().keySet());
  }

  /**
   * Communicates to the controller what cell has be clicked and changes that cell to the state
   * in the ParameterBox
   * @param columnNumber column number of the cell
   * @param rowNumber row number of the cell
   */
  public void changeCell(int columnNumber, int rowNumber){
    controller.changeCell(parameterBox.getState(), columnNumber, rowNumber);
  }

  /**
   * Updates the two views based on the current list of new states sent from the controller
   * @param myStates list of new states to be displayed
   */
  public void updateView(List<List<String>> myStates){
    mainGrid.updateGrid(myStates, config.getCellStyles());
    graph.updateGraph(myStates);
  }

  /**
   * Communicates to the controller what parameters should be changed
   * @param parameterList a map that contains the name of the parameter in the keyset and the new
   *                     parameter in the value set
   */
  public void updateParameters(Map<String, String> parameterList) {
    controller.updateParameters(parameterList);
  }

  /**
   * Displays an error message from the simulation to the CellularAutomatonView
   * @param errorMessage message to be displayed
   */
  public void makeAlert(String errorMessage) {
    parentView.makeAlert(errorMessage);
  }

}
