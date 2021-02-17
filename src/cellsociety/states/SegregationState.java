package cellsociety.states;


import cellsociety.CellState;

public class SegregationState extends CellState<SegregationState.States> {

  public enum States {
    X,
    O,
    OPEN
  }

  public SegregationState(States s) {
    super(s);
  }

  public SegregationState(){
    this(States.OPEN);
  }

  public SegregationState(String str) {
    super(States.class, str);
  }
}

