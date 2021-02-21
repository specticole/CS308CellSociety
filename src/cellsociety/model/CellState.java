package cellsociety.model;

import java.util.*;

/**
 * An immutable abstract type that represents the state of a single
 * grid cell at a single point in time.
 *
 * A CellState can take on one of a finite set of values, each of
 * which is communicated to and from the CellState as a
 * String.
 *
 * Internally, CellStates represent their state as an enum, which is
 * accessible to the parent CellState class, which is templated on
 * that enum type in a sort of Java-bastardized CRTP. (Sorry, I'm a
 * C++ programmer by training...)
 *
 * @author Franklin Wei
 */
public abstract class CellState<E extends Enum<E>> {
  private final E state;

  /**
   * Construct a CellState with a given enum value.
   *
   * @param s State enum value.
   */
  protected CellState(E s) {
    this.state = s;
  }

  /**
   * Construct a CellState with a string that corresponds to an enum
   * value.
   *
   * @param cl Class object corresponding to the child class's
   * internal state enum.
   * @param str String representing desired state enum value.
   */
  protected CellState(Class<E> cl, String str) throws IllegalArgumentException {
    this(Enum.valueOf(cl, str));
  }

  /**
   * Retrieve all available state names for this CellState variant.
   *
   * @return Collection of Strings representing all possible states
   * for this particular CellState.
   */
  public Collection<String> getAvailableStates() {
    return Arrays.asList(Arrays.stream(state.getDeclaringClass().getEnumConstants()).map(Enum::name).toArray(String[]::new));
  }

  /**
   * Retrieve state name as a String.
   *
   * @return The state name as a string.
   */
  @Override
  public String toString() {
    return state.toString();
  }

  /**
   * Retrieve the raw enum value of this state.
   *
   * @return The state name as a raw enum value.
   */
  public E getState() {
    return state;
  }
}
