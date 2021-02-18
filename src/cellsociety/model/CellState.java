package cellsociety.model;

import java.util.*;

/**
 * An immutable abstract type that represents the state of a single
 * grid cell at a single point in time.
 *
 * A CellState can take on one of a finite set of values, each of
 * which is communicated to and from the CellState as a
 * String. Internally, CellStates may choose to represent their state
 * as an enum type.
 */
public abstract class CellState<E extends Enum<E>> {
  private final E state;

  protected CellState(E s) {
    this.state = s;
  }

  protected CellState(Class<E> cl, String str) {
    this(Enum.valueOf(cl, str));
  }

  /**
   * Returns a Collection of all possible states for this CA
   * variation. Ideally, we'd make this an abstract static method, but
   * Java doesn't support such a thing (for no good reason, really --
   * it's a useful construct). So to use this, you need to instantiate
   * a default subclass (i.e. new GameOfLifeState()) and call
   * .getAvailableStates(), like this:
   *
   * Collection<String> states = new GameOfLifeState().getAvailableStates();
   */
  public Collection<String> getAvailableStates() {
    return Arrays.asList(Arrays.stream(state.getDeclaringClass().getEnumConstants()).map(Enum::name).toArray(String[]::new));
  }

  /**
   * @return the current state name as a string
   */
  @Override
  public String toString() {
    return state.toString();
  }

  public E getState() {
    return state;
  }
}
