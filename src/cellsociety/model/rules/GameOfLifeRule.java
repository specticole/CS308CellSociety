package cellsociety.model.rules;

import cellsociety.model.Cell;
import cellsociety.model.states.GameOfLifeState;
import cellsociety.model.CellularAutomatonRule;

import java.util.*;

/**
 * Implementation of Conway's Game of Life's next state logic.
 */
public class GameOfLifeRule extends CellularAutomatonRule {


  private Set<Integer> bornNums;
  private Set<Integer> surviveNums;

  /**
   * Construct a rule with the given parameter map.
   *
   * @param params Parameter map.
   */
  public GameOfLifeRule(Map<String, String> params) {
    super(params);

    setGameSpecifics(params);
  }

  /**
   * This method is called every step, and updates the cell's state under certain conditions
   *
   * @param cell this is the cell which's state will be updated
   * @param neighbors this is all 8 neighbors of cell
   */
  @Override
  public void advanceCellState(Cell cell, List<Cell> neighbors) {
    int aliveNeighbors = 0;
    for(Cell neighbor : neighbors){
      if(neighbor.getState(Cell.CURRENT_TIME).getState() == GameOfLifeState.States.ALIVE){
        aliveNeighbors++;
      }
    }

    //System.out.printf("Alive count: %d ", aliveNeighbors);

    if(cell.getState(Cell.CURRENT_TIME).getState()  == GameOfLifeState.States.ALIVE){
      if(!surviveNums.contains(aliveNeighbors)){
        cell.setState(Cell.NEXT_TIME, new GameOfLifeState(GameOfLifeState.States.DEAD));
        //System.out.printf("-> die");
      }
    } else {
      if(bornNums.contains(aliveNeighbors)){
        cell.setState(Cell.NEXT_TIME, new GameOfLifeState(GameOfLifeState.States.ALIVE));
        //Systemout.printf("-> born");
      }
    }
    //System.out.println("");
  }

  /**
   * This method gets the specific rule set for the game of live
   * variation, in the form of B(int)(int).../S(int)(int)...
   *
   * @param params Parameter map, with key "rules".
   */
  public void setGameSpecifics(Map<String, String> params) {
    List<Integer> born = new ArrayList<>();
    List<Integer> survive = new ArrayList<>();
    String rules = params.get("rules");
    String[] rulesSplit = rules.split("/");
    for (int i = 1; i < rulesSplit[0].length(); i++) {
      born.add(Integer.valueOf(rulesSplit[0].substring(i, i + 1)));
    }
    for (int i = 1; i < rulesSplit[1].length(); i++) {
      survive.add(Integer.valueOf(rulesSplit[1].substring(i, i + 1)));
    }

    bornNums = new HashSet<>();
    surviveNums = new HashSet<>();

    for(Integer i : born){
      bornNums.add(i);
    }
    for(Integer i : survive){
      surviveNums.add(i);
    }
  }
}
