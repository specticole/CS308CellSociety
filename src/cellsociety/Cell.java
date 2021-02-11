package cellsociety;

import cellsociety.rules.GameOfLifeRule;
import cellsociety.states.GameOfLifeState;

/**
 * A Cell is the basic unit of a CellGrid in a CellularAutomaton. Each
 * Cell holds its current and past CellStates in a CellStateList,
 * (which abstracts away the details of how exactly past states are
 * managed for memory purposes). A Cell always exists as a member
 * within exactly one parent CellGrid, which it has access to.
 *
 * A Cell knows its location within a grid through its
 * CellCoordinates, and it (or another class that has access to it)
 * can query its parent CellGrid for a List of its neighbors.
 *
 * @author Franklin Wei
 */
public class Cell {
  String gameType;
  CellState states;
  CellStateRule rules;
  public Cell(String gameType){
    //setGameType(gameType);
    setGameType("GameOfLife"); //will swap with above once we are actively creating cells

  }

  private void setGameType(String gameType){
    this.gameType = gameType;
    if(gameType.equals("GameOfLife")){
      states = new GameOfLifeState();
      rules = new GameOfLifeRule();
    }
  }

  public void setState(String state){
    states.fromString(state);
  }



  public String getState(){
    return states.toString();
  }
}
