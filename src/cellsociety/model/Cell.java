package cellsociety.model;

import cellsociety.model.util.*;

/**
 * A Cell is the basic unit of a CellGrid in a CellularAutomaton. Each
 * Cell holds its current and past CellStates in a StateList, (which
 * abstracts away the details of how exactly past states are managed
 * for memory purposes). A Cell always exists as a member within
 * exactly one parent CellGrid, which it can access through the
 * `parentGrid' member.
 *
 * The separation of a cell's location (handled by Cell), and its
 * states (which are handled by StateList) is crucial for adherence to
 * the single responsibility principle.
 *
 * A Cell knows its location within a grid through its
 * GridCoordinates, and it (or another class that has access to it)
 * can query its parent CellGrid for a List of its neighbors.
 *
 * @author Franklin Wei
 * @author Cole Spector
 */
public class Cell {
  /**
   * Constants representing offsets (deltas) from the current
   * time. Used to simplify state retrieval.
   *
   * CURRENT_TIME refers to the state parentGrid.currentTime + 0.
   */
  public final static int CURRENT_TIME = 0;

  /**
   * NEXT_TIME refers to the state parentGrid.currentTime + 1, and
   * should ONLY be used from implementations of
   * CellularAutomatonRule.
   */
  public final static int NEXT_TIME = 1;

  private StateList<CellState> states;
  private final CellGrid parentGrid;
  private final GridCoordinates coordinates;


  /**
   * Initialize a Cell with a given parent grid, and an empty
   * StateList.
   *
   * @param parentGrid Parent CellGrid.
   * @param coordinates Location on parent grid.
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
   * @param delta Time offset from parentGrid.currentTime.
   * @param state The new CellState to assign.
   */
  public void setState(int delta, CellState state) {
    states.setState(parentGrid.getCurrentTime() + delta, state);
  }

  /**
   * Retrieve the CellState of this cell at the time T=delta +
   * parentGrid.getCurrentTime(). If this is called from
   * advanceCellState(), delta=CURRENT_TIME denotes the "prior" parent
   * generation, and delta=NEXT_TIME denotes the "next" target
   * generation.
   *
   * Otherwise, delta=0 retrieves the latest generation's state.
   *
   * Allowing the retrieval up to 1 generation in the future allows
   * individual cells to coordinate and avoid multiple cells "moving"
   * into a single cell.
   *
   * IMPORTANT: *ONLY* implementations of CellularAutomatonRule may
   * call this method with delta=NEXT_TIME. Doing so from any other
   * code will raise an exception.
   *
   * @param delta Time offset from current time.
   * @return State at given time offset.
   */
  public CellState getState(int delta){
    return states.getState(parentGrid.getCurrentTime() + delta);
  }

  /**
   * This method returns the state list of this cell, which contains
   * the current and (some or all) prior states for this Cell.
   *
   * @return The StateList for this Cell.
   */
  public StateList<CellState> getStates() {
    return states;
  }

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
   * @param state State to append to state list.
   */
  public void appendState(CellState state) {
    states.addState(parentGrid.getCurrentTime() + 1,
                    state);
  }

  /**
   * This method returns the CellGrid of which this cell is a member.
   *
   * @return Parent CellGrid.
   */
  public CellGrid getParentGrid(){
    return parentGrid;
  }

  /**
   * Retrieve the coordinates of this cell.
   *
   * @return This cell's coordinates.
   */
  public GridCoordinates getCoordinates() {
    return coordinates;
  }
}
