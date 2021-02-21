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

public class WaTorParameterBox extends ParameterBox {

  private TextField fishRoundsToBreedField;
  private TextField sharkRoundsToBreedField;
  private TextField sharkRoundsToStarveField;

  public WaTorParameterBox(VBox newBox, ResourceBundle currentBundle,
      SimulationView currentSimulation){
    super(newBox, currentBundle, currentSimulation);
  }

  @Override
  public VBox createFields() {
    HBox fishRoundsToBreedBox = new HBox();
    fishRoundsToBreedBox.getStyleClass().add("parameter-box");
    Text fishRoundsToBreed = new Text();
    fishRoundsToBreed.setText(bundle.getString("fishRoundsToBreedLabel"));
    fishRoundsToBreedField = new TextField();
    fishRoundsToBreedField.setMaxWidth(TEXT_FIELD_MAX_WIDTH);
    fishRoundsToBreedBox.getChildren().addAll(fishRoundsToBreed, fishRoundsToBreedField);

    HBox sharkRoundsToBreedBox = new HBox();
    sharkRoundsToBreedBox.getStyleClass().add("parameter-box");
    Text sharkRoundsToBreed = new Text();
    sharkRoundsToBreed.setText(bundle.getString("sharkRoundsToBreedLabel"));
    sharkRoundsToBreedField = new TextField();
    sharkRoundsToBreedField.setMaxWidth(TEXT_FIELD_MAX_WIDTH);
    sharkRoundsToBreedBox.getChildren().addAll(sharkRoundsToBreed, sharkRoundsToBreedField);

    HBox sharkRoundsToStarveBox = new HBox();
    sharkRoundsToStarveBox.getStyleClass().add("parameter-box");
    Text sharkRoundsToStarve = new Text();
    sharkRoundsToStarve.setText(bundle.getString("sharkRoundsToStarveLabel"));
    sharkRoundsToStarveField = new TextField();
    sharkRoundsToStarveField.setMaxWidth(TEXT_FIELD_MAX_WIDTH);
    sharkRoundsToStarveBox.getChildren().addAll(sharkRoundsToStarve, sharkRoundsToStarveField);

    Button applyButton = new Button(bundle.getString("ApplyButtonLabel"));
    applyButton.setOnAction(e -> applyParameters());

    createCombobox();

    box.getChildren().addAll(fishRoundsToBreedBox, sharkRoundsToBreedBox, sharkRoundsToStarveBox,
        applyButton, states);
    return box;
  }

  private void createCombobox (){
    states = new ComboBox();
    states.setPromptText(bundle.getString("StatesPrompt"));
    states.getItems().addAll("EMPTY", "FISH", "SHARK");
  }

  @Override
  public void applyParameters(){
    parameterList.put("fishRoundsToBreed", fishRoundsToBreedField.getText());
    parameterList.put("sharkRoundsToBreed", sharkRoundsToBreedField.getText());
    parameterList.put("sharkRoundsToStarve", sharkRoundsToStarveField.getText());
    simulationView.updateParameters(parameterList);
  }
}
