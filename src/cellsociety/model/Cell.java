package cellsociety.model;

import cellsociety.model.util.*;

/**
 * A Cell is the basic unit of a CellGrid in a CellularAutomaton. Each
 * Cell holds its current and past CellStates in a CellStateList,
 * (which abstracts away the details of how exactly past states are
 * managed for memory purposes). A Cell always exists as a member
 * within exactly one parent CellGrid, which it can access through the
 * `parentGrid' member.
 *
 * A Cell knows its location within a grid through its
 * CellCoordinates, and it (or another class that has access to it)
 * can query its parent CellGrid for a List of its neighbors.
 *
 * @author Franklin Wei
 * @author Cole Spector
 */
public class Cell {
  /**
   * Constants representing offsets (deltas) from the current
   * time. Used to simplify state retrieval.
   */
  public final static int CURRENT_TIME = 0;
  public final static int NEXT_TIME = 1;

  private StateList<CellState> states;
  private final CellGrid parentGrid;
  private final GridCoordinates coordinates;


  /**
   * Initialize a Cell with a given parent grid, and an empty
   * StateList.
   */
  public Cell(CellGrid parentGrid, GridCoordinates coordinates){
    this.parentGrid = parentGrid;
    this.states = new StateList<>();
    this.coordinates = coordinates;
  }

  /**
   * Modify the CellState of this cell associated with a certain time
   * delta (relative to parentGrid.currentTime).
   *
   * @param state the string name of the state which to set the CellState
   */
  public void setState(int delta, CellState state) {
    states.setState(parentGrid.getCurrentTime() + delta, state);
  }

  /**
   * Retrieve the CellState of this cell at the time T=delta +
   * parentGrid.getCurrentTime(). If this is called from
   * advanceCellState(), delta=0 denotes the "prior" parent
   * generation, and delta=+1 denotes the "next" target generation.
   *
   * Otherwise, delta=0 retrieves the latest generation's state.
   *
   * Allowing the retrieval up to 1 generation in the future allows
   * individual cells to coordinate and avoid multiple cells "moving"
   * into a single cell.
   *
   * @return the current CellState as a string
   */
  public CellState getState(int delta){
    return states.getState(parentGrid.getCurrentTime() + delta);
  }

  /**
   * This method returns the StateList<Cell> of all current and prior
   * states for this Cell.
   *
   * @return the StateList for this Cell
   */
  public StateList<CellState> getStates() {
    return states;
  }


//  /**
//   * This method swaps the current state of two Cells
//   * @param otherCell the other Cell to be swapped with
//   */
//  public void swapCells(Cell otherCell){
//    CellState tempState = this.getState(CURRENT_TIME);
//    setState(NEXT_TIME, otherCell.getState(CURRENT_TIME));
//    otherCell.setState(NEXT_TIME, tempState);
//  }

  /**
   * Duplicate the latest state in our state list.
   *
   * It is _critically important_ that this is only called once per
   * time step of the parent grid's simulation time.
   */
  public void copyState() {
    states.addState(parentGrid.getCurrentTime() + 1,
                    states.getLatestState());
  }

  /**
   * Append a new CellState to our state list.
   *
   * It is _critically important_ that this is only called once per
   * time step of the parent grid's simulation time.
   *
   * @param state State to append.
   */
  public void appendState(CellState state) {
    states.addState(parentGrid.getCurrentTime() + 1,
                    state);
  }

  /**
   * This method returns the CellGrid the Cell is a part of
   * @return the CellGrid the Cell is a part of
   */
  public CellGrid getParentGrid(){
    return parentGrid;
  }

  public GridCoordinates getCoordinates() {
    return coordinates;
  }
}
