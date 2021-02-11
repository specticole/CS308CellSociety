package cellsociety;

public abstract class CellState {

  public CellState(){

  }

  public abstract String toString();

  public abstract CellState fromString(String str);
}
