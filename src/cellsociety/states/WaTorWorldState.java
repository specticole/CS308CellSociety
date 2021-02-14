package cellsociety.states;

import cellsociety.CellState;
import java.util.Collection;

public class WaTorWorldState extends CellState {

  private enum StateEnum {
    EMPTY,
    FISH,
    SHARK
  }

  private StateEnum state;
  private int turnsSurvived;

  WaTorWorldState(){
    state = StateEnum.EMPTY;
  }

  @Override
  public Collection<String> getAvailableStates() {
    return null;
  }

  @Override
  public String toString() {
    return state.toString();
  }

  @Override
  public CellState fromString(String str) {
    return null;
  }

  @Override
  public void setState(String str) {
    state = StateEnum.valueOf(str);
  }

  @Override
  public String getStateAsString() {
    return state.toString();
  }

  public int getTurnsSurvived(){
    return turnsSurvived;
  }



}
