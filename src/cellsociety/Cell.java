package cellsociety;

import cellsociety.rules.GameOfLifeRule;
import cellsociety.states.GameOfLifeState;

/**
 * A Cell is the basic unit of a CellGrid in a CellularAutomaton. Each
 * Cell holds its current and past CellStates in a CellStateList,
 * (which abstracts away the details of how exactly past states are
 * managed for memory purposes). A Cell always exists as a member
 * within exactly one parent CellGrid, which it has access to.
 *
 * A Cell knows its location within a grid through its
 * CellCoordinates, and it (or another class that has access to it)
 * can query its parent CellGrid for a List of its neighbors.
 *
 * @author Franklin Wei
 */
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
