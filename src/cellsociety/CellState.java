package cellsociety;

import java.util.Collection;
import cellsociety.CellState;

public abstract class CellState {
  /**
   * returns all possible states for this CA variation
   */
  public abstract Collection<String> getAvailableStates();

  /**
   *
   * @return the current state name as a string
   */
  public abstract String toString();

  /**
   * Sets the current state
   * @param str the String name of the desired state
   */
  public abstract Object fromString(String str);

  public abstract void setState(String str);

}
