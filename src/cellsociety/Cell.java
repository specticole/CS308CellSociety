package cellsociety;

import cellsociety.rules.GameOfLifeRule;
import cellsociety.states.GameOfLifeState;

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
    return states.getState();
  }
}
