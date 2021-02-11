package cellsociety;

import java.util.Collection;
import cellsociety.CellState;

public abstract class CellState {
  public abstract Collection<String> getAvailableStates();

  public abstract String toString();

  public abstract void fromString(String str);

}
