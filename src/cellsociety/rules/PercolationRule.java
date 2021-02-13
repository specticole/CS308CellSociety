package cellsociety.rules;

import cellsociety.Cell;
import cellsociety.CellState;
import cellsociety.CellStateRule;
import java.util.List;

public class PercolationRule implements CellStateRule {

  @Override
  public void nextCellState(Cell cell, List<Cell> neighbors) {
    if(cell.getState().equals("PERCOLATED")){
      for(Cell neighbor : neighbors){
        if(neighbor.getState().equals("OPEN")){
          neighbor.setState("PERCOLATED");
        }
      }
    }
  }


}
