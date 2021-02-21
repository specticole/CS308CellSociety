package cellsociety.model.states;

import cellsociety.model.CellState;

/**
 * A Fire cell state.
 *
 * @author Cole Spector
 */
public class FireState extends CellState<FireState.States> {

  /**
   * Enum of this state's allowed values.
   */
  public enum States {
    /**
     * Currently on fire.
     */
    BURNING,
    /**
     * Unburned tree.
     */
    TREE,
    /**
     * Empty space (cannot burn).
     */
    EMPTY
  }

  /**
   * Construct a FireState.
   *
   * @param s State.
   */
  public FireState(States s) {
    super(s);
  }

  /**
   * Construct a FireState (default to empty)
   */
  public FireState(){
    this(States.EMPTY);
  }

  /**
   * Construct a FireState.
   *
   * @param str State as a String.
   */
  public FireState(String str) {
    super(States.class, str);
  }
}
