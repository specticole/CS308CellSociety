package cellsociety.view.parameters;

import java.util.ResourceBundle;
import javafx.scene.layout.VBox;

public abstract class ParameterBox {

  protected VBox box;
  protected ResourceBundle bundle;

  public ParameterBox(VBox newBox, ResourceBundle currentBundle) {
    box = newBox;
    bundle = currentBundle;
  }

  public abstract void createFields();


}
