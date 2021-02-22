package cellsociety.model.rules;

import cellsociety.model.Cell;
import cellsociety.model.CellularAutomatonRule;
import cellsociety.model.states.RockPaperScissorState;
import cellsociety.model.states.RockPaperScissorState.States;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RockPaperScissorRule extends CellularAutomatonRule {

  private static final Map<States, List<States>> LOSSES_TO =
      Map.ofEntries(
        Map.entry(States.ROCK, Arrays.asList(States.PAPER)),
        Map.entry(States.PAPER, Arrays.asList(States.SCISSOR)),
        Map.entry(States.SCISSOR, Arrays.asList(States.ROCK))
  );
  private int threshold;

  public RockPaperScissorRule(Map<String, String> params) {
    super(params);
    setGameSpecifics(params);
  }

  @Override
  public void advanceCellState(Cell cell, List<Cell> neighbors) {
    int maxNeighborLosses = 0;
    States maxNeighborLossState = States.EMPTY;
    for(States state : LOSSES_TO.get(cell.getState(Cell.CURRENT_TIME).getState())){
      int numberOfLosses = getNeighborsOfState(state, neighbors);
      if( numberOfLosses >= threshold && numberOfLosses > maxNeighborLosses){
        maxNeighborLosses = numberOfLosses;
        maxNeighborLossState = state;
      }
    }
    if(maxNeighborLossState != States.EMPTY){
      cell.setState(Cell.NEXT_TIME, new RockPaperScissorState(maxNeighborLossState));
    }
  }

  private int getNeighborsOfState(States state, List<Cell> neighbors){
    int ret = 0;
    for (Cell neighbor : neighbors){
      if (neighbor.getState(Cell.CURRENT_TIME).getState() == state){
        ret ++;
      }
    }
    return ret;
  }

  /**
   * This method gets the specific rule set for the Segregation variation, in the form of <Int>
   * where the int is the number of neighbors which need to beat the cell in order for it to swap
   * @param params
   */
  public void setGameSpecifics(Map<String, String> params) {
    String rules = params.get("threshold");
    threshold = Integer.valueOf(rules);
  }
}
