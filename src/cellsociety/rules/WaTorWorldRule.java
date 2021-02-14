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

  private int[] usedNeighbors = {1,3,4,6};
  private int sharkRoundsToBreed;
  private int fishRoundsToBreed;


  public WaTorWorldRule(Map<String, String> params) {
    super(params);
  }

  @Override
  public void advanceCellState(Cell cell, List<Cell> neighbors) {
    if(cell.getState(Cell.CURRENT_TIME).getState() == WaTorWorldState.States.FISH){
      swapLogic(cell, neighbors);
    }

    if(cell.getState(Cell.CURRENT_TIME).getState() == WaTorWorldState.States.SHARK){
      if(!findFish(neighbors)){
        swapLogic(cell, neighbors);
      }
    }

    breed(cell.getState(Cell.CURRENT_TIME), neighbors);





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
          spawn.appendState(new WaTorWorldState(States.FISH));
        }
      } else if(state.getState() == States.SHARK){
        if(state.getTurnsSurvived() >= sharkRoundsToBreed){
          spawn.appendState(new WaTorWorldState(States.SHARK));
        }
      }

    }


  }

  private ArrayList<Cell> getUsefulNeighbors(List<Cell> neighbors, WaTorWorldState.States state){
    ArrayList<Cell> emptyNeighbors = new ArrayList<>();
    for (int neighborIndex : usedNeighbors) {
      Cell neighbor = neighbors.get(neighborIndex);
      if(neighbor.getState(Cell.CURRENT_TIME).getState() == state){
        emptyNeighbors.add(neighbor);
      }
    }
    return emptyNeighbors;
  }

  private Boolean findFish(List<Cell> neighbors){
    ArrayList<Cell> possibleFood = new ArrayList<>();
    possibleFood = getUsefulNeighbors(neighbors, WaTorWorldState.States.FISH);

    if(possibleFood.size() > 0){
      Random rand = new Random();
      possibleFood.get(rand.nextInt(possibleFood.size())).appendState(new WaTorWorldState(States.EMPTY));
      return true;
    }
    return false;
  }


  private void swapLogic(Cell currentCell, List<Cell> neighbors) {
    ArrayList<Cell> possibleSwaps = new ArrayList<>();
    for (int neighborIndex : usedNeighbors) {
      Cell neighbor = neighbors.get(neighborIndex);
      if(neighbor.getState(Cell.CURRENT_TIME).getState() == WaTorWorldState.States.EMPTY){
        int currentTime = currentCell.getParentGrid().getCurrentTime();
        if(neighbor.getStates().getLatestTime() == currentTime){
          possibleSwaps.add(neighbor);
        } else if (neighbor.getState(Cell.NEXT_TIME).getState() == WaTorWorldState.States.EMPTY){
          possibleSwaps.add(neighbor);
        }
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
   */
  public void setGameSpecifics() {
    //this will change to read in from the xml

    sharkRoundsToBreed = 3;
    fishRoundsToBreed = 3;
  }
}
