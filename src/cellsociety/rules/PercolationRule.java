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
    if(cell.getState(cell.CURRENT_TIME).getState() == PercolationState.States.OPEN){
      for(Cell neighbor : neighbors){
        if(neighbor.getState(cell.CURRENT_TIME).getState() == PercolationState.States.PERCOLATED){
          cell.setState(cell.NEXT_TIME, new PercolationState(PercolationState.States.PERCOLATED));
          break;
        }
      }
    }
  }
}
