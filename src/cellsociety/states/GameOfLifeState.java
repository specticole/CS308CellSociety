package cellsociety.states;

import cellsociety.CellState;

public class GameOfLifeState extends CellState {

  private enum StateEnum {
    DEAD,
    ALIVE
  }

  StateEnum state;

  GameOfLifeState(){
    state = DEAD;
  }

  public String toString() {
    return state.toString();
  }

  public static CellState fromString(String str) {
    return StateEnum.valueOf(str);
  }
}
