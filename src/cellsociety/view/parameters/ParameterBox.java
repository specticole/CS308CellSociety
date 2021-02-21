package cellsociety.view.parameters;

import cellsociety.view.SimulationView;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.layout.VBox;

public abstract class ParameterBox {

  public static final int TEXT_FIELD_MAX_WIDTH = 75;

  protected VBox box;
  protected Map<String, String> parameterList;
  protected ResourceBundle bundle;
  protected SimulationView simulationView;

  public ParameterBox(VBox newBox, ResourceBundle currentBundle, SimulationView currentSimulation) {
    box = newBox;
    bundle = currentBundle;
    simulationView = currentSimulation;
    parameterList = new HashMap<>();
  }

  public abstract VBox createFields();

  public abstract void applyParameters();


}
