package cellsociety.states;

import cellsociety.CellState;

public class PercolationState extends CellState<PercolationState.States> {

  public enum States {
    BLOCKED,
    OPEN,
    PERCOLATED
  }

  public PercolationState(States s) {
    super(s);
  }

  public PercolationState(){
    this(States.OPEN);
  }

  public PercolationState(String str) {
    super(States.class, str);
  }
}
