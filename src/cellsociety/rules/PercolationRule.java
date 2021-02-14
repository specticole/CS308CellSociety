package cellsociety.rules;

import cellsociety.Cell;
import cellsociety.CellState;
import cellsociety.CellularAutomatonRule;
import cellsociety.states.PercolationState;
import java.util.*;

/**
 * Implementation of Percolation's next state logic.
 */
public class PercolationRule extends CellularAutomatonRule {
  public PercolationRule(Map<String, String> params) {
    super(params);
  }

  @Override
  public void advanceCellState(Cell cell, List<Cell> neighbors) {
    if(cell.getState(0).getState() == PercolationState.States.OPEN){
      for(Cell neighbor : neighbors){
        if(neighbor.getState(0).getState() == PercolationState.States.PERCOLATED){
          cell.setState(1, new PercolationState(PercolationState.States.PERCOLATED));
          break;
        }
      }
    }
  }
}
