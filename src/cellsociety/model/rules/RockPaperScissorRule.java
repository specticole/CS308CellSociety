package cellsociety.model.rules;

import cellsociety.model.Cell;
import cellsociety.model.CellularAutomatonRule;
import cellsociety.model.states.RockPaperScissorState;
import cellsociety.model.states.RockPaperScissorState.States;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of rock-paper-scissors as a cellular automaton.
 */
public class RockPaperScissorRule extends CellularAutomatonRule {

  private static final Map<States, List<States>> LOSSES_TO =
      Map.of(
          States.ROCK, Arrays.asList(States.PAPER),
          States.PAPER, Arrays.asList(States.SCISSOR),
          States.SCISSOR, Arrays.asList(States.ROCK),
          States.EMPTY, Arrays.asList(States.ROCK, States.SCISSOR, States.PAPER)
      );
  private int threshold;

  /**
   * Construct a rule.
   *
   * @param params Parameter map.
   */
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
   * This method gets the specific rule set for the Segregation
   * variation, in the form of (Int) where the int is the number of
   * neighbors which need to beat the cell in order for it to swap.
   *
   * @param params Parameter map
   */
  @Override
  public void setGameSpecifics(Map<String, String> params) {
    threshold = 3; // default value
    if (params.containsKey("threshold")) {
      int paramThreshold = 0;
      try {
        paramThreshold = Integer.parseInt(params.get("threshold"));
      }
      catch (Exception ignored) {

      }
      if (paramThreshold > 0) {
        threshold = paramThreshold;
      }
    }
  }
}
