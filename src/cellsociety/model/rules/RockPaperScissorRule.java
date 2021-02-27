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
 *
 * @author Cole Spector
 *
 *
 * Implementation of CellularAutomatonRule for Conway's Game of Life simulation.
 * This class will be called for every step of the simulation, and is used
 * to update its GameOfLife state accordingly.
 *
 * This method assumes that it is passed a Map, with a key: "rules" ->
 * a string "B(int)(int).../S(int)(int)..."
 *
 * This class relies on Cell.java and FireState.java
 *
 * Example:
 *
 * CellGrid grid = new CellGrid(...);
 *
 * Map<String, String> params = new Map();
 * String threshold = "3";
 * String rules = "threshold";
 * params.set(rules, threshold);
 * RockPaperScissorRule rockPaperScissorRule = new RockPaperScissorRule(params);
 * grid.copyState();
 *
 * for(Cell c : grid)
 * rockPaperScissorRule.advanceCellState(c, grid.getNeighbors(c));
 *
 * grid.advanceCurrentTime();
 *
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
