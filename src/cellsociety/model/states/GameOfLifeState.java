package cellsociety.model.states;

import cellsociety.model.CellState;

/**
 *
 * @author Franklin Wei
 * @author Cole Spector
 *
 * This is an immutible class, white stores a Cell.java's GameOfLifeState at a certain time.
 *
 * This class expects an enumerated type, States, as a parameter, which must be one of the following:
 * DEAD,
 * ALIVE
 *
 *
 *
 * Example:
 *
 * StateList<GameOfLifeState> stateList = new StateList<>();
 * GameOfLifeState gameOfLifeState(stateList) = new GameOfLifeState();
 *
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
