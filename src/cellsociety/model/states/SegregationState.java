package cellsociety.model.states;


import cellsociety.model.CellState;

/**
 *
 *
 * @author Cole Spector
 *
 * This is an immutible class, white stores a Cell.java's SegregationState at a certain time.
 *
 * This class expects an enumerated type, States, as a parameter, which must be one of the following:
 * X,
 * O,
 * OPEN,
 *
 *
 * Example:
 *
 * StateList<SegregationState> stateList = new StateList<>();
 * SegregationState segregationState(stateList) = new SegregationState();
 *
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
