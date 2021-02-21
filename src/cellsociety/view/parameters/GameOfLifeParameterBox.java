package cellsociety.view.parameters;

import cellsociety.view.SimulationView;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class GameOfLifeParameterBox extends ParameterBox{

  private TextField bornNumsField;
  private TextField surviveNumsField;

  public GameOfLifeParameterBox(VBox newBox, ResourceBundle currentBundle,
      SimulationView currentSimulation){
    super(newBox, currentBundle, currentSimulation);
  }

  @Override
  public VBox createFields() {
    HBox bornNumsBox = new HBox();
    bornNumsBox.getStyleClass().add("parameter-box");
    Text bornNums = new Text();
    bornNums.setText(bundle.getString("bornNumsLabel"));
    bornNumsField = new TextField();
    bornNumsField.setMaxWidth(TEXT_FIELD_MAX_WIDTH);
    bornNumsBox.getChildren().addAll(bornNums, bornNumsField);

    HBox surviveNumsBox = new HBox();
    surviveNumsBox.getStyleClass().add("parameter-box");
    Text surviveNums = new Text();
    surviveNums.setText(bundle.getString("surviveNumsLabel"));
    surviveNumsField = new TextField();
    surviveNumsField.setMaxWidth(TEXT_FIELD_MAX_WIDTH);
    surviveNumsBox.getChildren().addAll(surviveNums, surviveNumsField);

    Button applyButton = new Button(bundle.getString("ApplyButtonLabel"));
    applyButton.setOnAction(e -> applyParameters());

    box.getChildren().addAll(bornNumsBox, surviveNumsBox, applyButton);
    return box;
  }

  @Override
  public void applyParameters(){
    parameterList.put("bornNums", bornNumsField.getText());
    parameterList.put("surviveNums", surviveNumsField.getText());
    simulationView.updateParameters(parameterList);
  }
}
