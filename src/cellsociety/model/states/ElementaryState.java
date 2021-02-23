package cellsociety.model.states;

import cellsociety.model.CellState;

/**
 * A an elementary cell state in a 1-D cellular automaton (see
 * https://en.wikipedia.org/wiki/Elementary_cellular_automaton).
 *
 * @author Franklin Wei
 */
public class ElementaryState extends CellState<ElementaryState.States> {

  /**
   * Enum of this state's allowed values.
   */
  public enum States {
    /**
     * Dead cell.
     */
    DEAD,
    /**
     * Live cell.
     */
    ALIVE
  }

  /**
   * Construct a ElementaryState.
   *
   * @param s State.
   */
  public ElementaryState(States s) {
    super(s);
  }

  /**
   * Construct a ElementaryState (defaulting to DEAD).
   */
  public ElementaryState(){
    this(States.DEAD);
  }

  /**
   * Construct a ElementaryState.
   *
   * @param str State as a String.
   */
  public ElementaryState(String str) {
    super(States.class, str);
  }
}
