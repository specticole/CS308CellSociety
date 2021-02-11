package cellsociety;

import java.util.ArrayList;

public class CellStateList {
  private ArrayList<String> stateList;
  private int statesToKeep;

  public CellStateList(){
    this(0);
  }
  public CellStateList(int statesToKeep){
    stateList = new ArrayList<>();
    this.statesToKeep = statesToKeep;
  }

  public void addState(String state){
    stateList.add(state);
  }

}
