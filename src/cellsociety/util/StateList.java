package cellsociety.util;

import java.util.Collections;
import java.util.TreeMap;
import java.util.Map;
import java.util.Set;

/**
 * A utility class to keep track of a series of "states" (which can be
 * any templated type), each of which is associated with an integer
 * "time". Times of states added to the list must be non-negative and
 * should form a continuous integer range starting from zero.
 *
 * However, a StateList does not necessarily need to hold all states
 * going back to T=0 -- a StateList holds up to `statesToKeep` states
 * at once, or an infinite amount of states, if `statesToKeep' is the
 * symbolic value INFINITE_STATES.
 *
 * @author Franklin Wei
 * @author Cole Spector
 */
public class StateList<T> {
  /**
   * Special integer value denoting that an infinite number of states
   * should be retained. Guaranteed to be unambiguous (i.e. not a
   * strictly positive integer).
   */
  public static final int INFINITE_STATES = 0;

  /**
   * Default number of states to keep. Can be INFINITE_STATES.
   */
  public static final int DEFAULT_STATES_TO_KEEP = INFINITE_STATES;

  private Map<Integer, T> timeToState;
  private int statesToKeep;

  public StateList(int statesToKeep) {
    timeToState = new TreeMap<>();
    this.statesToKeep = statesToKeep;
  }

  public StateList() {
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

    if(keySet.isEmpty())
      return true; // empty list is a consistent state

    int min = Collections.min(keySet);
    int max = Collections.max(keySet);

    /* times must be non-negative */
    assert(min >= 0 && max >= 0);

    /* ensure that times form a continuous range */
    for(int i = min; i <= max; i++) {
      if(!keySet.contains(i))
        return false;
    }

    /*
     * We know that the states held in this list span the continuous
     * range of times from [ min, max ] -- but we don't know if this
     * is the right range. Check that now:
     */
    int expectedMin = 0;

    if(statesToKeep != INFINITE_STATES && max >= statesToKeep)
      expectedMin = max - statesToKeep + 1;

    return min == expectedMin;
  }

  private Set<Integer> getKeySet() {
    return timeToState.keySet();
  }

  /**
   * Retrieves the most recent time associated with a state, or -1 if
   * the list is empty.
   *
   * @return the latest time associated with a state, as an integer,
   * or -1 if the list is empty.
   */
  public int getLatestTime(){
    if(getKeySet().isEmpty())
      return -1;
    return Collections.max(getKeySet());
  }

  /**
   * Retrieves the oldest time associated with a state, or -1 if the
   * list is empty.
   *
   * @return the oldest time associated with a state, as an integer,
   * or -1 if the list is empty.
   */
  public int getOldestTime() {
    if(getKeySet().isEmpty())
      return -1;
    return Collections.min(getKeySet());
  }

  /**
   * Returns the total number of states present in this list. Equal to
   * getLatestTime() - getOldestTime() + 1.
   *
   * @return Number of states present.
   */
  public int statesPresent() {
    int present = timeToState.size();

    // check consistency
    assert(present == getLatestTime() - getOldestTime() + 1);

    return present;
  }

  private void deleteState(int time) {
    timeToState.remove(time);
  }

  /**
   * Add a new state associated with time `time`, which must be equal
   * to getLatestTime() + 1 (which is zero if the list is empty)
   *
   * @param time Time associated with state. Must be getLatestTime() +
   * 1.
   * @param state State to associate with this time.
   */
  public void addState(int time, T state) {
    assert(time >= 0 && time == getLatestTime() + 1);

    timeToState.put(time, state);

    if(statesToKeep != INFINITE_STATES &&
       statesPresent() > statesToKeep)
      deleteState(getOldestTime());

    assert(checkIntegrity());
  }

  /**
   * Retrieve a state associated with a time.
   *
   * @return State associated with time.
   */
  public T getState(int time) {
    assert(time >= 0);

    return timeToState.get(time);
  }

  public String toString() {
    return timeToState.toString();
  }
}
