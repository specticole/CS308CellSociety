package cellsociety.model.states;

import cellsociety.model.CellState;

/**
 * A RPS cell state.
 *
 * @author Cole Spector
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
