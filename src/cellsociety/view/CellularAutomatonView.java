package cellsociety.view;

import javafx.scene.layout.StackPane;

public class CellularAutomatonView {

  StackPane stackPane;
  GridStyle gridStyle;

  public CellularAutomatonView(GridStyle gridStyle){
    stackPane = new StackPane();
    this.gridStyle = gridStyle;
  }

  public StackPane getPane(){
    return stackPane;
  }

}
