package cellsociety.model.rules;

import cellsociety.model.Cell;
import cellsociety.model.CellularAutomatonRule;
import cellsociety.model.states.FireState;
import cellsociety.model.states.FireState.States;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Implementation of Fire's time evolution rule.
 *
 * @author Cole Spector
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
    cell.setState(Cell.NEXT_TIME, new FireState(States.BURNING));
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
  @Override
  public void setGameSpecifics(Map<String, String> params) {
    fireChance = 50; // default value
    if (params.containsKey("probCatch")) {
      int inputChance = Integer.parseInt(params.get("probCatch"));
      if (inputChance >= 0 && inputChance <= 100) {
        fireChance = inputChance;
      }
    }
  }
}
