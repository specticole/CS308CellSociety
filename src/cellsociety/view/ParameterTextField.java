package cellsociety.view;

import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Pair;

public class ParameterTextField extends TextField {

  public static final int TEXT_FIELD_MAX_WIDTH = 75;

  private String parameterName;
  private TextField parameterTextField;

  public ParameterTextField(String name){
    parameterName = name;
  }

  public HBox initialize(){
    HBox parameterEntryBox = new HBox();
    parameterEntryBox.getStyleClass().add("parameter-box");
    Text label = new Text();
    label.setText(parameterName);
    parameterTextField = new TextField();
    parameterTextField.setMaxWidth(TEXT_FIELD_MAX_WIDTH);

    parameterEntryBox.getChildren().addAll(label, parameterTextField);

    return parameterEntryBox;
  }

  public Pair<String, String> getParameterChange(){
    return new Pair<>(parameterName, parameterTextField.getText());
  }
}
