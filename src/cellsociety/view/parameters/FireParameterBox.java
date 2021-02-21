package cellsociety.view.parameters;

import cellsociety.view.SimulationView;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class FireParameterBox extends ParameterBox{

  private TextField probCatchField;

  public FireParameterBox(VBox newBox, ResourceBundle currentBundle,
      SimulationView currentSimulation){
    super(newBox, currentBundle, currentSimulation);
  }

  @Override
  public VBox createFields() {
    HBox probCatchBox = new HBox();
    probCatchBox.getStyleClass().add("parameter-box");
    Text probCatch = new Text();
    probCatch.setText(bundle.getString("probCatchLabel"));
    probCatchField = new TextField();
    probCatchField.setMaxWidth(TEXT_FIELD_MAX_WIDTH);
    probCatchBox.getChildren().addAll(probCatch, probCatchField);

    Button applyButton = new Button(bundle.getString("ApplyButtonLabel"));
    applyButton.setOnAction(e -> applyParameters());

    createCombobox();

    box.getChildren().addAll(probCatchBox, applyButton, states);
    return box;
  }

  private void createCombobox (){
    states = new ComboBox();
    states.setPromptText(bundle.getString("StatesPrompt"));
    states.getItems().addAll("BURNING", "TREE", "EMPTY");
  }

  @Override
  public void applyParameters(){
    parameterList.put("probCatch", probCatchField.getText());
    simulationView.updateParameters(parameterList);
  }
}
