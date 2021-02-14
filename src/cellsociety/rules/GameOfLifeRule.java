package cellsociety.rules;

import cellsociety.Cell;
import cellsociety.CellState;
import cellsociety.states.GameOfLifeState;
import cellsociety.CellularAutomatonRule;

import java.util.*;

/**
 * Implementation of Conway's Game of Life's next state logic.
 */
public class GameOfLifeRule extends CellularAutomatonRule {


  private Set<Integer> bornNums;
  private Set<Integer> surviveNums;

  public GameOfLifeRule(Map<String, String> params) {
    super(params);

    getGameSpecifics();
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
      if(neighbor.getState(cell.CURRENT_TIME).getState() == GameOfLifeState.States.ALIVE){
        aliveNeighbors++;
      }
    }

    //System.out.printf("Alive count: %d ", aliveNeighbors);

    if(cell.getState(cell.CURRENT_TIME).getState()  == GameOfLifeState.States.ALIVE){
      if(!surviveNums.contains(aliveNeighbors)){
        cell.setState(cell.NEXT_TIME, new GameOfLifeState(GameOfLifeState.States.DEAD));
        //System.out.printf("-> die");
      }
    } else {
      if(bornNums.contains(aliveNeighbors)){
        cell.setState(cell.NEXT_TIME, new GameOfLifeState(GameOfLifeState.States.ALIVE));
        //Systemout.printf("-> born");
      }
    }
    //System.out.println("");
  }

  /**
   * This method gets the specific rule set for the game of live variation, in the form of B<int><int>.../S<int><int>...
   */
  public void getGameSpecifics() {
    //this will change to read in from the xml
    Integer[] born = {3};
    Integer[] survive = {2, 3};

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
