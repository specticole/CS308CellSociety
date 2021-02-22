package cellsociety.model.states;


import cellsociety.model.CellState;

/**
 * A Segregation cell state.
 *
 * @author Cole Spector
 */
public class SegregationState extends CellState<SegregationState.States> {

  /**
   * Enum of this state's allowed values.
   */
  public enum States {
    /**
     * Person of type "X".
     */
    X,
    /**
     * Person of type "O"
     */
    O,
    /**
     * Empty space.
     */
    OPEN
  }

  /**
   * Construct a SegregationState.
   *
   * @param s State.
   */
  public SegregationState(States s) {
    super(s);
  }

  /**
   * Construct a empty SegregationState (defaults to open).
   */
  public SegregationState(){
    this(States.OPEN);
  }

  /**
   * Construct a SegregationState
   *
   * @param str State as a String.
   */
  public SegregationState(String str) {
    super(States.class, str);
  }
}
