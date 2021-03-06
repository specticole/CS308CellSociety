package cellsociety.model.states;

import cellsociety.model.CellState;

/**
 *
 *
 * @author Cole Spector
 *
 * This is an immutible class, white stores a Cell.java's RockPaperScissorState at a certain time.
 *
 * This class expects an enumerated type, States, as a parameter, which must be one of the following:
 * Rock,
 * Paper,
 * SCISSOR,
 * Empty
 *
 *
 * Example:
 *
 * StateList<RockPaperScissorState> stateList = new StateList<>();
 * RockPaperScissorState rockPaperScissorState(stateList) = new RockPaperScissorState();
 *
 */
public class RockPaperScissorState extends CellState<RockPaperScissorState.States> {

  /**
   * Allowed state enum.
   */
  public enum States {
    /**
     * Rock
     */
    ROCK,
    /**
     * Paper
     */
    PAPER,
    /**
     * Scissors
     */
    SCISSOR,
    /**
     * Empty cell.
     */
    EMPTY
  }

  /**
   * Create a state.
   *
   * @param s State enum.
   */
  public RockPaperScissorState(States s) {
    super(s);
  }

  /**
   * Create an empty state.
   */
  public RockPaperScissorState(){
    this(States.EMPTY);
  }

  /**
   * Create a state.
   *
   * @param str State string.
   */
  public RockPaperScissorState(String str) {
    super(States.class, str);
  }
}
