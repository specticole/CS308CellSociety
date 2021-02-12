package cellsociety.rules;

import cellsociety.Cell;
import cellsociety.CellState;
import cellsociety.CellStateRule;
import cellsociety.states.WaTorWorldState;
import java.util.List;


public class WaTorWorldRule implements CellStateRule {

  @Override
  public void nextCellState(Cell cell, List<Cell> neighbors) {
    CellState cellState = cell.getState();
    if(cellState.getStateAsString().equals("FISH")){
      for(Cell neighbor : neighbors){

      }
    }


  }


  private void swapLogic(Cell currentCell, Cell otherCell){
    int currentTime = currentCell.getParentGrid().getCurrentTime();
    if(otherCell.getState().getStateAsString().equals("OPEN")){
      if(otherCell.getPriorStates())
    }
  }
}
