package cellsociety;


import java.util.ArrayList;

/**
 * An (important) utility class that keeps track of the past
 * CellStates of a Cell.
 *
 * @author Franklin Wei
 * @author Cole Spector
 */
public class CellStateList {

  private ArrayList<String> stateList;
  private int statesToKeep;

  public CellStateList() {
    this(0);
  }

  public CellStateList(int statesToKeep) {
    stateList = new ArrayList<>();
    this.statesToKeep = statesToKeep;
  }

  public void addState(String state) {
    stateList.add(state);
  }
}