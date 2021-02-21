package cellsociety.view;

import cellsociety.CellularAutomatonConfiguration;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SimulationView {

  private GridPane masterLayout;
  private HBox titleBox;
  private GridStyle mainGrid;
  private Pane pane;
  private HBox graphBox;
  private Graph graph;
  private Button showGraphButton;
  private VBox editSimulationBox;

  private CellularAutomatonConfiguration config;
  private ResourceBundle bundle;

  public SimulationView(CellularAutomatonConfiguration newConfig, ResourceBundle currentBundle){
    masterLayout = new GridPane();
    masterLayout.getStyleClass().add("simulation-gridpane");
    config = newConfig;
    bundle = currentBundle;
  }

  public GridPane initialize(){
    createTitle();
    createGraphButton();
    createGrid();

    masterLayout.add(titleBox, 0,0);
    masterLayout.add(graphBox, 0,1);
    masterLayout.add(pane, 1,0, 1,2);
    return masterLayout;
  }

  private void createGraphButton() {
    graphBox = new HBox();
    graphBox.getStyleClass().add("");
    showGraphButton = new Button(bundle.getString("ShowGraphButtonLabel"));
    showGraphButton.setOnAction(e -> showGraph());
    graphBox.getChildren().add(showGraphButton);
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
    pane = mainGrid.createGrid(config.getGridWidth(), config.getGridHeight());
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

  private void showGraph(){

  }



}
