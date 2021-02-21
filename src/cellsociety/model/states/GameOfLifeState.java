package cellsociety.model.states;

import cellsociety.model.CellState;

/**
 * A Game of Life cell state.
 *
 * @author Franklin Wei
 */
public class GameOfLifeState extends CellState<GameOfLifeState.States> {

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
   * Construct a GameOfLifeState.
   *
   * @param s State.
   */
  public GameOfLifeState(States s) {
    super(s);
  }

  /**
   * Construct a GameOfLifeState (defaulting to DEAD).
   */
  public GameOfLifeState(){
    this(States.DEAD);
  }

  /**
   * Construct a GameOfLifeState.
   *
   * @param str State as a String.
   */
  public GameOfLifeState(String str) {
    super(States.class, str);
  }
}
