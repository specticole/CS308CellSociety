package cellsociety;

import cellsociety.rules.GameOfLifeRule;
import cellsociety.states.GameOfLifeState;

public class Cell {
  private CellState states;
  private CellStateList priorStates;
  public Cell(){
    //setGameType(gameType);
    priorStates = new CellStateList();

  }

  /**
   * This method sets the state of the current cell
   *
   * @param state the string name of the state which to set the state to
   */
  public void setState(String state){
    priorStates.addState(state);
    states.fromString(state);
  }


  /**
   * This method returns the current state as a string
   * @return the current state as a string
   */
  public String getState(){
    return states.toString();
  }
}
