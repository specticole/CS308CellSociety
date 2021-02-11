package cellsociety.states;

import static java.lang.System.exit;

import cellsociety.CellState;
import java.util.ArrayList;
import java.util.Collection;

public class GameOfLifeState extends CellState {

  private enum StateEnum {
    DEAD,
    ALIVE
  }

  StateEnum state;
  private ArrayList<String> availableStates;


  public GameOfLifeState(){
    state = StateEnum.DEAD;
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

  @Override
  public Object fromString(String str) {
    return StateEnum.valueOf(str);
  }

  /**
   * Sets the current state
   * @param str the String name of the desired state
   */
  @Override
  public void setState(String str) {
    if(availableStates.contains(str)){
      state = StateEnum.valueOf(str);
    } else {
      System.out.printf("Input state %s is invalid, please input a valid state", str);
      exit(0);
    }
  }
}
