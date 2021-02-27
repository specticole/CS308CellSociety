package cellsociety.model.states;

import cellsociety.model.CellState;

/**
 *
 * @author Franklin Wei
 * @author Cole Spector
 *
 * This is an immutible class, white stores a Cell.java's PercolationState at a certain time.
 *
 * This class expects an enumerated type, States, as a parameter, which must be one of the following:
 * BLOCKED,
 * OPEN,
 * PERCOLATED,
 *
 *
 * Example:
 *
 * StateList<PercolationState> stateList = new StateList<>();
 * PercolationState percolationState(stateList) = new PercolationState();
 *
 */
public class PercolationState extends CellState<PercolationState.States> {
  /**
   * Possible states.
   */
  public enum States {
    /**
     * An obstacle.
     */
    BLOCKED,
    /**
     * Unpercolated.
     */
    OPEN,
    /**
     * Filled with "water".
     */
    PERCOLATED
  }

  /**
   * Construct a PercolationState.
   *
   * @param s State.
   */
  public PercolationState(States s) {
    super(s);
  }

  /**
   * Construct an OPEN PercolationState.
   */
  public PercolationState(){
    this(States.OPEN);
  }

  /**
   * Construct a PercolationState.
   *
   * @param str State as a String.
   */
  public PercolationState(String str) {
    super(States.class, str);
  }
}
