package cellsociety.model.states;

import cellsociety.model.CellState;

/**
 * Percolation cell state.
 *
 * @author Cole Spector
 */
public class LangtonState extends CellState<LangtonState.States> {

  protected LangtonState(States s) {
    super(s);
  }

  /**
   * Possible states.
   */
  public enum States {

    //No cell data
    OPEN,

    //A trail for the TURN and ADVANCE TO FOLLOW
    PATH,

    //The sheath around a loop
    SHEATH,

    //The trailer behind a moving state
    TRAILING,

    //If hits a SHEATH, and not other path is OPEN, extends the PATH by one
    ADVANCE,

    //If hits a SHEATH, and no other path is OPEN, sets SHEATH to HOLD
    //If hits a HOLD, and no other path is open, extends 90 deg. clockwise one.
    //If hits a SHEATH, and two paths are OPEN, sets SHEATH to MESSENGER.
    TURN,

    //Tells turn whether or not to create a new turn.
    HOLD,

    //Sets branching PATH to SHEATH-OPEN-SHEATH, with OPEN above and below middle OPEN
    MESSENGER,

    //IF loops collide, sets end of loop to killer, killing all TURN and ADVANCE in loop.


    /**
     * If hits a SHEATH, and no other path is OPEN, sets SHEATH to
     * HOLD If hits a HOLD, and no other path is open, extends 90
     * deg. clockwise one.  If hits a SHEATH, and two paths are OPEN,
     * sets SHEATH to MESSENGER.
     */
    TURN,

    /**
     * Tells turn whether or not to create a new turn.
     */
    HOLD,

    /**
     * Sets branching PATH to SHEATH-OPEN-SHEATH, with OPEN above and
     * below middle OPEN
     */
    MESSENGER,

    /**
     * IF loops collide, sets end of loop to killer, killing all TURN and ADVANCE in loop.
  }

  /**
   * Construct a PercolationState.
   *
   * @param s State.
   */


  /**
   * Construct an OPEN PercolationState.
   */
  public LangtonState(){
    this(States.OPEN);
  }

  /**
   * Construct a PercolationState.
   *
   * @param str State as a String.
   */
  public LangtonState(String str) {
    super(States.class, str);
  }
}
