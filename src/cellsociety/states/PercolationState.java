package cellsociety.states;

import static java.lang.System.exit;

import cellsociety.CellState;
import cellsociety.states.GameOfLifeState.States;
import java.util.ArrayList;
import java.util.Collection;

public class PercolationState extends CellState {

  public enum StateEnum {
    BLOCKED,
    OPEN,
    PERCOLATING
  }

  PercolationState.StateEnum state;
  private ArrayList<String> availableStates;


  public PercolationState(States s){
    super(s);
    state = StateEnum.OPEN;
    availableStates = new ArrayList<>();
    for(StateEnum state : StateEnum.values()){
      availableStates.add(state.toString());
    }
  }

  /**
   * returns all possible states for this CA variation
   */
  @Override
  public Collection<String> getAvailableStates() {
    return availableStates;
  }

  /**
   *
   * @return the current state name as a string
   */
  @Override
  public String toString() {
    return state.toString();
  }


  public CellState fromString(String str) {
    return null;
  }


  /**
   * Sets the current state
   * @param str the String name of the desired state
   */

  public void setState(String str) {
    if(availableStates.contains(str)){
      state = StateEnum.valueOf(str);
    } else {
      System.out.printf("Input state %s is invalid, please input a valid state", str);
      exit(0);
    }
  }
  
  public String getStateAsString() {
    return state.toString();
  }
}
