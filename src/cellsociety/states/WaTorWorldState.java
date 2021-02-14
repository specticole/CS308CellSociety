package cellsociety.states;

import cellsociety.CellState;


public class WaTorWorldState extends CellState<WaTorWorldState.States> {

  public enum States {
    EMPTY,
    FISH,
    SHARK
  }

  private int turnsSurvived;



  public WaTorWorldState(States s, int survived) {
    super(s);
    turnsSurvived = survived;
  }



  public WaTorWorldState(States s) {
    this(s, 0);

  }

  public WaTorWorldState(){
    this(States.EMPTY);
  }

  public WaTorWorldState(String str) {
    super(States.class, str);
  }

  public int getTurnsSurvived(){
    return turnsSurvived;
  }

}
