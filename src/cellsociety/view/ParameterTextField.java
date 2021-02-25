package cellsociety.view;

import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Pair;

/**
 * An extension of the TextField JavaFX object that attaches a corresponding String that
 * represents the name of the TextField. Used by the ParameterBox object to be able to
 * differentiate which parameter is changing per TextField.
 *
 * @author Bill Guo
 */

public class ParameterTextField extends TextField {

  public static final int TEXT_FIELD_MAX_WIDTH = 75;

  private String parameterName;
  private TextField parameterTextField;

  /**
   * Constructor of a ParameterTextField
   * @param name name of the Parameter to be changed
   */
  public ParameterTextField(String name){
    parameterName = name;
  }

  /**
   * Handles the creation of UI elements per ParameterTextField.
   * @return the HBox that contains the label and TextField of the ParameterTextField
   */
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

  /**
   * Returns the value in the TextField as well as the corresponding parameter name
   * @return a pair of name and value to be put into the parameterMap in the ParameterBox
   */
  public Pair<String, String> getParameterChange(){
    return new Pair<>(parameterName, parameterTextField.getText());
  }
}
