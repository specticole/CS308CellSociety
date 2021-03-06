package cellsociety.model.rules;

import cellsociety.model.Cell;
import cellsociety.model.CellularAutomatonRule;

import cellsociety.model.states.PercolationState;
import java.util.*;

/**
 * Implementation of Percolation's next state logic.
 *
 * @author Franklin Wei
 */
public class PercolationRule extends CellularAutomatonRule {
  /**
   * Construct a Percolation rule. `params' is ignored.
   *
   * @param params ignored
   */
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

  @Override
  public void setGameSpecifics(Map<String, String> params) {

  }
}
