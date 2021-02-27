package cellsociety.model.states;

import cellsociety.model.CellState;

/**
 *
 *
 * @author Cole Spector
 *
 * This is an immutible class, white stores a Cell.java's WaTorWorldState at a certain time.
 *
 * This class expects an enumerated type, States, as a parameter, which must be one of the following:
 * X,
 * O,
 * OPEN,
 *
 *
 * Example:
 *
 * StateList<WaTorWorldState> stateList = new StateList<>();
 * WaTorWorldState waTorWorldState(stateList) = new WaTorWorldState();
 *
 */
public class WaTorWorldState extends CellState<WaTorWorldState.States> {

  /**
   * Possible states.
   */
  public enum States {
    /**
     * Just water.
     */
    EMPTY,
    /**
     * A fish.
     */
    FISH,
    /**
     * A shark.
     */
    SHARK
  }

  private int turnsSurvived;
  private int turnsWithoutEating;

  /**
   * Construct a Wa-Tor state.
   *
   * @param s State.
   * @param survived Number of turns survived.
   * @param eating Number of turns without eating.
   */
  public WaTorWorldState(States s, int survived, int eating){
    super(s);
    turnsSurvived = survived;
    turnsWithoutEating = eating;
  }

  /**
   * Construct a Wa-Tor state.
   *
   * @param s State.
   * @param survived Number of turns survived.
   */
  public WaTorWorldState(States s, int survived){
    this(s, survived, 0);
  }


  /**
   * Construct a Wa-Tor state.
   *
   * @param s State.
   */
  public WaTorWorldState(States s) {
    this(s, 0);
  }

  /**
   * Construct an empty Wa-Tor state.
   */
  public WaTorWorldState(){
    this(States.EMPTY);
  }

  /**
   * Construct a Wa-Tor state.
   *
   * @param str State as a String.
   */
  public WaTorWorldState(String str) {
    super(States.class, str);
  }

  /**
   * @return turnsSurvived
   */
  public int getTurnsSurvived(){
    return turnsSurvived;
  }

  /**
   * @return turnsWithoutEating
   */
  public int getTurnsWithoutEating(){
    return turnsWithoutEating;
  }

}
