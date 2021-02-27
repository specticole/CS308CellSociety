package cellsociety.model.rules;

import cellsociety.model.Cell;
import cellsociety.model.CellularAutomatonRule;
import cellsociety.model.states.FireState;
import cellsociety.model.states.FireState.States;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author Cole Spector
 *
 *
 * Implementation of CellularAutomatonRule for the Fire simulation.
 * This class will be called for every step of the simulation, and is used
 * to update its CellState (FireState.java) and decides if a TREE becomes BURNING,
 * or if a BURNING tree dies out (becomes EMPTY).
 *
 * This method assumes that it is passed a Map, with a key: "probCatch" -> an integer representing the percent chance of
 * fire.
 *
 * This class relies on Cell.java and FireState.java
 *
 * Example:
 *
 * CellGrid grid = new CellGrid(...);
 *
 * Map<String, String> params = new Map();
 * String fireChance = "50";
 * String rules = "probCatch";
 * params.set(rules, fireChance);
 * FireRule fireRule = new FireRule(params);
 * grid.copyState();
 *
 * for(Cell c : grid)
 * fireRule.advanceCellState(c, grid.getNeighbors(c));
 *
 * grid.advanceCurrentTime();
 *
 */
public class FireRule extends CellularAutomatonRule {

  private int fireChance;

  /**
   * Construct a FireRule with the given parameter map.
   *
   * @param params Parameter map.
   */
  public FireRule(Map<String, String> params) {
    super(params);
    setGameSpecifics(params);
  }

  @Override
  public void advanceCellState(Cell cell, List<Cell> neighbors) {
    if(cell.getState(Cell.CURRENT_TIME).getState() == States.TREE){
      neighborsOnFire(cell, neighbors);
    }
    if (cell.getState(Cell.CURRENT_TIME).getState() == States.BURNING){
      dieOut(cell);
    }
  }

  private void neighborsOnFire(Cell cell, List<Cell> neighbors){
    for(Cell neighbor : neighbors){
      if (neighbor.getState(Cell.CURRENT_TIME).getState() == States.BURNING &&
          didCatchFire()) {
        burn(cell);
        break;
      }
    }
  }

  private boolean didCatchFire(){
    Random rand = new Random();
    return (rand.nextInt(100) <= fireChance);
  }

  private void dieOut(Cell cell){
    cell.setState(Cell.NEXT_TIME, new FireState(States.EMPTY));
  }

  private void burn(Cell cell){
    cell.setState(Cell.NEXT_TIME, new FireState(States.BURNING));
  }



  /**
   * This method gets the specific rule set for the Fire variation,
   * with one parameter:
   *
   * "probCatch" -> an integer representing the percent chance of
   * fire.
   *
   * @param params Parameter map.
   */
  public void setGameSpecifics(Map<String, String> params) {
    String rules = params.get("probCatch");
    fireChance = Integer.valueOf(rules);
  }
}
