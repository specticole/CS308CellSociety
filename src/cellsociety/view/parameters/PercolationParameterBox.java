package cellsociety.view.parameters;

import cellsociety.view.ParameterBox;
import cellsociety.view.SimulationView;
import java.util.ResourceBundle;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;


public class PercolationParameterBox extends ParameterBox {

  public PercolationParameterBox(VBox newBox, ResourceBundle currentBundle,
      SimulationView currentSimulation){
    super(newBox, currentBundle, currentSimulation);
  }

  @Override
  public VBox createFields() {
    createCombobox();

    box.getChildren().addAll(states);
    return box;
  }

  private void createCombobox (){
    states = new ComboBox();
    states.setPromptText(bundle.getString("StatesPrompt"));
    states.getItems().addAll("OPEN", "BLOCKED", "PERCOLATED");
  }

  @Override
  public void applyParameters() {
  //no parameters to change
  }

}
