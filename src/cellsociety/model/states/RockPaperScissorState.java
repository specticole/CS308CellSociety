package cellsociety.model.states;

import cellsociety.model.CellState;

public class RockPaperScissorState extends CellState<RockPaperScissorState.States> {

  public enum States {
    ROCK,
    PAPER,
    SCISSOR,
    EMPTY
  }

  public RockPaperScissorState(States s) {
    super(s);
  }

  public RockPaperScissorState(){
    this(States.EMPTY);
  }

  public RockPaperScissorState(String str) {
    super(States.class, str);
  }
}
