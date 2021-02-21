package cellsociety.view;

import cellsociety.view.SimulationView;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;

public abstract class ParameterBox {

  public static final int TEXT_FIELD_MAX_WIDTH = 75;

  protected VBox box;
  protected Map<String, String> parameterList;
  protected ResourceBundle bundle;
  protected SimulationView simulationView;
  protected ComboBox states;

  public ParameterBox(VBox newBox, ResourceBundle currentBundle, SimulationView currentSimulation) {
    box = newBox;
    box.getStyleClass().add("parameter-box");
    bundle = currentBundle;
    simulationView = currentSimulation;
    parameterList = new HashMap<>();
  }

  public abstract VBox createFields();

  public abstract void applyParameters();

  public String getState(){
    return (String) states.getValue();
  }


}
