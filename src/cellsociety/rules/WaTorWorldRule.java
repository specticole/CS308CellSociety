package cellsociety.rules;

import cellsociety.Cell;
import cellsociety.CellState;
import cellsociety.CellularAutomatonRule;
import cellsociety.states.WaTorWorldState;
import cellsociety.states.WaTorWorldState.States;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class WaTorWorldRule extends CellularAutomatonRule {

  //private static final int TOP_NEIGHBOR = 1;
  //private static final int LEFT_NEIGHBOR = 3;
  //private static final int RIGHT_NEIGHBOR = 4;
  //private static final int BOTTOM_NEIGHBOR = 6;

  //private int[] usedNeighbors = {TOP_NEIGHBOR,LEFT_NEIGHBOR,RIGHT_NEIGHBOR,BOTTOM_NEIGHBOR};
  private int sharkRoundsToBreed;
  private int fishRoundsToBreed;


  public WaTorWorldRule(Map<String, String> params) {
    super(params);
    setGameSpecifics(params);
  }

  @Override
  public void advanceCellState(Cell cell, List<Cell> neighbors) {
    if(cell.getState(Cell.CURRENT_TIME).getState() == WaTorWorldState.States.FISH){
      swapLogic(cell, neighbors);
      updateTurnsSurvived(cell, States.FISH);
    }

    if(cell.getState(Cell.CURRENT_TIME).getState() == WaTorWorldState.States.SHARK){
      if(!findFish(neighbors)){
        swapLogic(cell, neighbors);
      }
      updateTurnsSurvived(cell, States.SHARK);
    }

    breed(cell.getState(Cell.CURRENT_TIME), neighbors);

  }

  private void updateTurnsSurvived(Cell cell, WaTorWorldState.States state){
    WaTorWorldState currentState = (WaTorWorldState) cell.getState(Cell.CURRENT_TIME);
    int newTurnsSurvived = currentState.getTurnsSurvived() + 1;
    cell.setState(Cell.CURRENT_TIME, new WaTorWorldState(state, newTurnsSurvived));

  }

  private void breed(CellState cellState, List<Cell> neighbors){
    WaTorWorldState state = (WaTorWorldState) cellState;
    ArrayList<Cell> possibleSpawn = new ArrayList<>();
    possibleSpawn = getUsefulNeighbors(neighbors, WaTorWorldState.States.EMPTY);

    if(possibleSpawn.size() > 0){
      Random rand = new Random();
      Cell spawn = possibleSpawn.get(rand.nextInt(possibleSpawn.size()));

      if(state.getState() == States.FISH){
        if(state.getTurnsSurvived() >= fishRoundsToBreed){
          spawn.setState(Cell.CURRENT_TIME, new WaTorWorldState(States.FISH));
        }
      } else if(state.getState() == States.SHARK){
        if(state.getTurnsSurvived() >= sharkRoundsToBreed){
          spawn.setState(Cell.CURRENT_TIME, new WaTorWorldState(States.SHARK));
        }
      }

    }


  }

  private ArrayList<Cell> getUsefulNeighbors(List<Cell> neighbors, WaTorWorldState.States state){
    ArrayList<Cell> usefulNeighbors = new ArrayList<>();
    for (Cell neighbor : neighbors) {
      if(neighbor.getState(Cell.CURRENT_TIME).getState() == state){
        usefulNeighbors.add(neighbor);
      }
    }
    return usefulNeighbors;
  }

  private Boolean findFish(List<Cell> neighbors){
    ArrayList<Cell> possibleFood = new ArrayList<>();
    possibleFood = getUsefulNeighbors(neighbors, WaTorWorldState.States.FISH);
    for(Cell food : possibleFood){
      if(food.getState(Cell.NEXT_TIME).getState() != States.EMPTY){
        possibleFood.remove(food);
      }
    }

    if(possibleFood.size() > 0){
      Random rand = new Random();
      Cell food = possibleFood.get(rand.nextInt(possibleFood.size()));
      food.setState(Cell.CURRENT_TIME, new WaTorWorldState(States.EMPTY));
      return true;
    }
    return false;
  }


  private void swapLogic(Cell currentCell, List<Cell> neighbors) {
    ArrayList<Cell> possibleSwaps = new ArrayList<>();
    possibleSwaps = getUsefulNeighbors(neighbors, WaTorWorldState.States.EMPTY);
    for (Cell neighbor : possibleSwaps) {
      if (neighbor.getState(Cell.NEXT_TIME).getState() != WaTorWorldState.States.EMPTY){
        possibleSwaps.remove(neighbor);
      }
    }
    if(possibleSwaps.size() > 0){
      Random rand = new Random();
      currentCell.swapCells(possibleSwaps.get(rand.nextInt(possibleSwaps.size())));
    }
  }

  /**
   * This method gets the specific rule set for the game of live variation, in the form of F<int>.../S<int>...
   * where F is the number of rounds a fish needs to survive in order to breed
   * where S is the number of rounds a shark needs to survive in order to breed
   * @param params
   */
  public void setGameSpecifics(Map<String, String> params) {
    String rules = params.get("rules");
    String[] rulesSplit = rules.split("/");
    fishRoundsToBreed = Integer.valueOf(rulesSplit[0].substring(1));
    sharkRoundsToBreed = Integer.valueOf(rulesSplit[1].substring(1));
  }
}
