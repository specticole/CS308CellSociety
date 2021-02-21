package cellsociety.view.parameters;

import cellsociety.view.ParameterBox;
import cellsociety.view.SimulationView;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SegregationParameterBox extends ParameterBox {

  private TextField neighborsNeededField;

  public SegregationParameterBox(VBox newBox, ResourceBundle currentBundle,
      SimulationView currentSimulation){
    super(newBox, currentBundle, currentSimulation);
  }

  @Override
  public VBox createFields() {
    HBox neighborsNeededBox = new HBox();
    neighborsNeededBox.getStyleClass().add("parameter-box");
    Text neighborsNeeded = new Text();
    neighborsNeeded.setText(bundle.getString("neighborsNeededLabel"));
    neighborsNeededField = new TextField();
    neighborsNeededField.setMaxWidth(TEXT_FIELD_MAX_WIDTH);
    neighborsNeededBox.getChildren().addAll(neighborsNeeded, neighborsNeededField);

    Button applyButton = new Button(bundle.getString("ApplyButtonLabel"));
    applyButton.setOnAction(e -> applyParameters());

    createCombobox();

    box.getChildren().addAll(neighborsNeededBox, applyButton, states);
    return box;
  }

  private void createCombobox (){
    states = new ComboBox();
    states.setPromptText(bundle.getString("StatesPrompt"));
    states.getItems().addAll("X", "O", "OPEN");
  }

  @Override
  public void applyParameters(){
    parameterList.put("neighborsNeeded", neighborsNeededField.getText());
    simulationView.updateParameters(parameterList);
  }
}
