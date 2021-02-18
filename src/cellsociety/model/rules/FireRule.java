package cellsociety.model.rules;

import cellsociety.model.Cell;
import cellsociety.model.CellularAutomatonRule;
import cellsociety.model.states.FireState;
import cellsociety.model.states.FireState.States;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class FireRule extends CellularAutomatonRule {

  private int fireChance;

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

  public void neighborsOnFire(Cell cell, List<Cell> neighbors){
    for(Cell neighbor : neighbors){
      if (neighbor.getState(Cell.CURRENT_TIME).getState() == States.BURNING &&
          didCatchFire()) {
        burn(cell);
        break;
      }
    }
  }

  public boolean didCatchFire(){
    Random rand = new Random();
    return (rand.nextInt(100) <= fireChance);
  }

  public void dieOut(Cell cell){
    cell.setState(Cell.NEXT_TIME, new FireState(States.BURNING));
  }

  public void burn(Cell cell){
    cell.setState(Cell.NEXT_TIME, new FireState(States.BURNING));
  }



  /**
   * This method gets the specific rule set for the Segregation variation, in the form of <Int>
   * where the double is the chance out of 100 (as a whole number) of a TREE catching fire with
   * a BURNING neighbor
   * @param params
   */
  public void setGameSpecifics(Map<String, String> params) {
    String rules = params.get("probCatch");
    fireChance = Integer.valueOf(rules);
  }
}
