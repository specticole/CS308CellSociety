package cellsociety.states;

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

  @Override
  public Collection<String> getAvailableStates() {
    return availableStates;
  }

  @Override
  public String toString() {
    return state.toString();
  }

  @Override
  public String getState(){
    return state.toString();
  }

  @Override
  public void fromString(String str) {
    state = StateEnum.valueOf(str);
  }
}
