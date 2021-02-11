package cellsociety.states;

import cellsociety.CellState;
import java.util.Collection;

public class WaTorWorldState extends CellState {

  private enum StateEnum {
    EMPTY,
    FISH,
    SHARK
  }

  StateEnum state;
  int turnsSurvived;

  WaTorWorldState(){
    state = StateEnum.EMPTY;
  }

  @Override
  public Collection<String> getAvailableStates() {
    return null;
  }

  public String toString() {
    return state.toString();
  }

  @Override
  public Object fromString(String str) {
    return StateEnum.valueOf(str);
  }

  @Override
  public void setState(String str) {
    state = StateEnum.valueOf(str);
  }

}
