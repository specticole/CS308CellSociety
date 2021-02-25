package cellsociety.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

/**
 * A VBox that can be used by the user to change the parameters and states of the corresponding
 * simulation. Each ParameterBox takes in a list of parameters that can be changed, as well as a
 * list of states to change to when a cell is clicked.
 *
 * @author Bill Guo
 */

public class ParameterBox {

  private VBox box;
  private ResourceBundle bundle;
  private SimulationView simulationView;
  private Map<String, String> parameterMap;
  private Set<String> states;
  private List<ParameterTextField> parameterTextFieldsList;
  private ComboBox stateList;

  /**
   * The constructor of a ParameterBox.
   *
   * @param newBox the VBox that will contain the TextFields for each parameter and the list of
   *               states
   * @param currentBundle the language of the labels
   * @param currentSimulation the parent simulation so that the updated parameters can be sent to
   *                         the controller
   * @param mapOfParameters the list of parameters and their initial values
   * @param listOfStates the list of states
   */
  public ParameterBox(VBox newBox, ResourceBundle currentBundle,
      SimulationView currentSimulation, Map<String, String> mapOfParameters,
      Set<String> listOfStates) {
    box = newBox;
    box.getStyleClass().add("parameter-box");
    bundle = currentBundle;
    simulationView = currentSimulation;
    parameterMap = mapOfParameters;
    states = listOfStates;
  }

  /**
   * Returns the state selected by the user in the list of states
   * @return the state selected in the ComboBox of states
   */
  public String getState(){
    return (String) stateList.getValue();
  }

  public VBox initialize(){
    createParameterTextFields();
    if(!parameterTextFieldsList.isEmpty()){
      createButton();
    }
    createCombobox();
    return box;
  }

  private void createParameterTextFields() {
    parameterTextFieldsList = new ArrayList<>();
    for (String parameter : parameterMap.keySet()){
      ParameterTextField newField = new ParameterTextField(parameter);
      parameterTextFieldsList.add(newField);
      box.getChildren().add(newField.initialize());
    }
  }

  private void createButton() {
    Button applyButton = new Button(bundle.getString("ApplyButtonLabel"));
    applyButton.setOnAction(e -> applyParameters());
    box.getChildren().add(applyButton);
  }

  private void createCombobox (){
    stateList = new ComboBox();
    stateList.setPromptText(bundle.getString("StatesPrompt"));
    for(String state: states){
      stateList.getItems().add(state);
    }
    box.getChildren().add(stateList);
  }

  private void applyParameters(){
    for (ParameterTextField parameterTextField : parameterTextFieldsList){
      Pair<String, String> info = parameterTextField.getParameterChange();
      parameterMap.put(info.getKey(), info.getValue());
    }
    simulationView.updateParameters(parameterMap);
  }
}
