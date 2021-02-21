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
  private Pane gridView;
  private VBox buttonBox;
  private Graph graph;
  private Button graphButton;
  private Button gridButton;
  private VBox editSimulationBox;

  private CellularAutomatonConfiguration config;
  private ResourceBundle bundle;

  private boolean graphShown;
  private boolean gridShown;

  public SimulationView(CellularAutomatonConfiguration newConfig, ResourceBundle currentBundle){
    masterLayout = new GridPane();
    masterLayout.getStyleClass().add("simulation-gridpane");
    config = newConfig;
    bundle = currentBundle;
    graphShown = false;
    gridShown = true;
  }

  public GridPane initialize(){
    createTitle();
    createButtons();
    createGrid();

    masterLayout.add(titleBox, 0,0);
    masterLayout.add(buttonBox, 0,1);
    masterLayout.add(gridView, 1,0, 1,3);
    return masterLayout;
  }

  private void createButtons() {
    buttonBox = new VBox();
    buttonBox.getStyleClass().add("");
    graphButton = new Button(bundle.getString("ShowGraphButtonLabel"));
    graphButton.setOnAction(e -> toggleGraph());
    buttonBox.getChildren().add(graphButton);

    gridButton = new Button(bundle.getString("HideGridButtonLabel"));
    gridButton.setOnAction(e -> toggleGrid());
    buttonBox.getChildren().add(gridButton);
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
    gridView = mainGrid.createGrid(config.getGridWidth(), config.getGridHeight());
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



}
