package cellsociety.states;

import cellsociety.CellState;

public class FireState extends CellState<FireState.States> {

  public enum States {
    BURNING,
    TREE,
    EMPTY
  }

  public FireState(States s) {
    super(s);
  }

  public FireState(){
    this(States.EMPTY);
  }

  public FireState(String str) {
    super(States.class, str);
  }
}