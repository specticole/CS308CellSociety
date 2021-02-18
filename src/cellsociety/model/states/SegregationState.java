package cellsociety.model.states;


import cellsociety.model.CellState;

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

