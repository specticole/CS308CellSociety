package cellsociety.states;

import cellsociety.CellState;

public class WaTorWorldState extends CellState {

  private enum StateEnum {
    EMPTY,
    FISH,
    SHARK
  }

  StateEnum state;
  int turnsSurvived;

  WaTorWorldState(){
    state = DEAD;
  }

  public String toString() {
    return state.toString();
  }

  public static CellState fromString(String str) {
    return StateEnum.valueOf(str);
  }
}
