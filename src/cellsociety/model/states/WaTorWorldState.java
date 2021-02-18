package cellsociety.model.states;

import cellsociety.model.CellState;


public class WaTorWorldState extends CellState<WaTorWorldState.States> {

  public enum States {
    EMPTY,
    FISH,
    SHARK
  }

  private int turnsSurvived;
  private int turnsWithoutEating;


  public WaTorWorldState(States s, int survived, int eating){
    super(s);
    turnsSurvived = survived;
    turnsWithoutEating = eating;
  }

  public WaTorWorldState(States s, int survived){
    this(s, survived, 0);
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

  public int getTurnsWithoutEating(){
    return turnsWithoutEating;
  }

}
