package cellsociety.rules;

import cellsociety.Cell;
import cellsociety.CellState;
import cellsociety.CellularAutomatonRule;
import java.util.List;
import java.util.Map;

public class PercolationRule extends CellularAutomatonRule {

  public PercolationRule(Map<String, String> params) {
    super(params);
  }

  @Override
  public void advanceCellState(Cell cell, List<Cell> neighbors) {
//    if(cell.getState().equals("PERCOLATED")){
//      for(Cell neighbor : neighbors){
//        if(neighbor.getState().equals("OPEN")){
//          neighbor.setState("PERCOLATED");
//        }
//      }
//    }
  }

}
