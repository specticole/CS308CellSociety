package cellsociety;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * An (important) utility class that keeps track of the past
 * CellStates of a Cell.
 *
 * A CellStateList holds up to `statesToKeep` CellStates at once.
 *
 * @author Franklin Wei
 * @author Cole Spector
 */
public class CellStateList {
  private static final int INFINITE_STATES = 0;
  private static final int DEFAULT_STATES_TO_KEEP = INFINITE_STATES;

  private Map<Integer, CellState> timeToState;
  private int statesToKeep;

  public CellStateList(int statesToKeep) {
    timeToState = new HashMap<>();
    this.statesToKeep = statesToKeep;
  }

  public CellStateList() {
    this(DEFAULT_STATES_TO_KEEP);
  }

  /**
   * Checks that the keys in timeToState form a continuous integer
   * range from [start .. end].
   *
   * Case 1: time <= statesToKeep
   * [ time = 0, .. time_end < statesToKeep ]
   *
   * Case 2: time > statesToKeep
   * [ time_end - statesToKeep + 1 .. time_end ]
   */
  private boolean checkIntegrity() {
    Set<Integer> keySet = timeToState.keySet();
    int min = Collections.min(keySet);
    int max = Collections.max(keySet);

    boolean valid = true;
    for(int i = min; i <= max; i++) {
      valid &= keySet.contains(i);
    }

    return valid;

  }

  /**
   * Gets the latest time associated with a CellState
   * @return the latest time associated with a CellState as an Integer
   */
  public int getLatestTime(){
    Set<Integer> keySet = timeToState.keySet();
    return Collections.max(keySet);
  }

  /**
   * Add a new state associated with time `time`.
   *
   * @param time
   * @param state
   */
  public void addState(int time, CellState state) {
    assert(time >= 0);
  }

  /**
   * Return the CellState associated with time.
   */
  public CellState getState(int time) {
    assert(time >= 0);
    return null;
  }
}
