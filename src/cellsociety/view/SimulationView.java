package cellsociety.view;

import cellsociety.CellularAutomatonConfiguration;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SimulationView {

  private GridPane masterLayout;
  private HBox titleBox;
  private GridStyle mainGrid;
  private Graph graph;
  private VBox editSimulationBox;

  private CellularAutomatonConfiguration config;

  public SimulationView(CellularAutomatonConfiguration newConfig){
    masterLayout = new GridPane();
    config = newConfig;
  }

  public GridPane initialize(){
    titleBox = new HBox();
    titleBox.getStyleClass().add("title-box");
    Text titleText = new Text();
    titleText.setText(config.getSimulationMetadata().get("title"));
    masterLayout.add(titleBox, 0,0);
    switch (config.getGridType()){
      case "rectangular":
        mainGrid = new RectangularGridStyle(new GridPane());
        break;
      case "hexagonal":
        mainGrid = new HexagonalGridStyle(new Pane());
        break;
    }



    return masterLayout;
  }

  private void createTitle(){
    //titleBox = new HBox();
    //title.getStyleClass().add("title-box");

    //Text titleText = new Text(config.getSimulationMetadata().get("title"));
    //titleText.getStyleClass().add("text-style");

    //title.getChildren().add(titleText);
    //masterLayout.add(title, 0,0,4,1);
  }



}
