package cellsociety.states;

import cellsociety.CellState;
import cellsociety.states.GameOfLifeState.States;
import java.util.Collection;

public class WaTorWorldState extends CellState {

  private enum StateEnum {
    EMPTY,
    FISH,
    SHARK
  }

  private StateEnum state;
  private int turnsSurvived;

  public WaTorWorldState(States s){
    super(s);
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


  public CellState fromString(String str) {
    return null;
  }



  public void setState(String str) {
    state = StateEnum.valueOf(str);
  }


  public String getStateAsString() {
    return state.toString();
  }

  public int getTurnsSurvived(){
    return turnsSurvived;
  }



}
