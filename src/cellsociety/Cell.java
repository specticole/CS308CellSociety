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
 * @author Cole Spector
 */
public class Cell {
  private CellState state;
  private CellStateList priorStates;
  private CellGrid parentGrid;
  public Cell(CellGrid parentGrid){
    this.parentGrid = parentGrid;
    priorStates = new CellStateList();

  }

  /**
   * This method sets the CellState of the current Cell
   *
   * @param state the string name of the state which to set the CellState to
   */
  public void setState(String state){
    int newTime = parentGrid.getCurrentTime() + 1;
    priorStates.addState(newTime, this.state.fromString(state));
  }


  /**
   * This method returns the current CellState as a string
   * @return the current CellState as a string
   */
  public CellState getState(){
    return state;
  }


  /**
   * This method returns the CellStateList of all prior states for this Cell
   * @return the CellStateList for this Cell
   */
  public CellStateList getPriorStates(){
    return priorStates;
  }

  public void swapCells(Cell otherCell){
    //TODO: have this cell swap all info but prior states with otherCell
  }


  /**
   * This method returns the CellGrid the Cell is a part of
   * @return the CellGrid the Cell is a part of
   */
  public CellGrid getParentGrid(){
    return parentGrid;
  }
}
