package cellsociety;

public abstract class CellState {
  public abstract static Collection<String> availableStates();

  public abstract String toString();

  public static abstract CellState fromString(String str);
}
