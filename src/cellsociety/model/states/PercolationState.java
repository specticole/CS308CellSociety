package cellsociety.model.states;

import cellsociety.model.CellState;

/**
 * Percolation cell state.
 *
 * @author Franklin Wei
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
