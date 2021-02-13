package cellsociety;

import java.util.*;
import cellsociety.CellState;

/**
 * An immutable abstract type that represents the state of a single
 * grid cell at a single point in time.
 *
 * A CellState can take on one of a finite set of values, each of
 * which is communicated to and from the CellState as a
 * String. Internally, CellStates may choose to represent their state
 * as an enum type.
 */
public abstract class CellState {
  /**
   * Returns a Collection of all possible states for this CA
   * variation. Ideally, we'd make this an abstract static method, but
   * Java doesn't support such a thing (for no good reason, really --
   * it's a useful construct).
   */
  public abstract Collection<String> getAvailableStates();

  /**
   * @return the current state name as a string
   */
  @Override
  public abstract String toString();

  /**
   * Convert an enum type to collection of strings.
   */
  protected static Collection<String> enumToStringList(Class<? extends Enum<?>> e) {
    // from https://stackoverflow.com/questions/13783295/getting-all-names-in-an-enum-as-a-string
    return Arrays.asList(Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new));
  }
}
