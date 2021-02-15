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
  private int sharkRoundsToStarve;
  private int fishRoundsToBreed;



  public WaTorWorldRule(Map<String, String> params) {
    super(params);
    setGameSpecifics(params);
  }

  @Override
  public void advanceCellState(Cell cell, List<Cell> neighbors) {
    if(cell.getState(Cell.CURRENT_TIME).getState() == WaTorWorldState.States.FISH){

      updateTurnsSurvived(cell);

      swapLogic(cell, neighbors);

    }

    if(cell.getState(Cell.CURRENT_TIME).getState() == WaTorWorldState.States.SHARK){
      updateTurnsSurvived(cell);

      if(findFish(neighbors)) {

        foundFood(cell);
      } else {
        if(!starve(cell)){
          swapLogic(cell, neighbors);
        }


      }

    }

    breed(cell.getState(Cell.CURRENT_TIME), neighbors);

  }


  private boolean starve(Cell cell){
    WaTorWorldState state = (WaTorWorldState) cell.getState(Cell.CURRENT_TIME);
    //System.out.printf("----%d turns without eating\n", state.getTurnsWithoutEating());
    if(state.getTurnsWithoutEating() >= sharkRoundsToStarve){
      cell.setState(Cell.NEXT_TIME, new WaTorWorldState());
      cell.setState(Cell.CURRENT_TIME, new WaTorWorldState());
      return true;
    }
    return false;
  }

  private void updateTurnsSurvived(Cell cell){
    WaTorWorldState currentState = (WaTorWorldState) cell.getState(Cell.CURRENT_TIME);
    int newTurnsSurvived = currentState.getTurnsSurvived() + 1;
    int newTurnsWithoutEating = currentState.getTurnsWithoutEating() + 1;
    cell.setState(Cell.CURRENT_TIME, new WaTorWorldState(currentState.getState(), newTurnsSurvived, newTurnsWithoutEating));

  }

  private void breed(CellState cellState, List<Cell> neighbors){
    WaTorWorldState state = (WaTorWorldState) cellState;
    ArrayList<Cell> possibleSpawn = new ArrayList<>();
    possibleSpawn = getUsefulNeighbors(neighbors, WaTorWorldState.States.EMPTY);
    //System.out.printf("A----%d\n", possibleSpawn.size());
    possibleSpawn.removeIf(cell -> cell.getState(Cell.NEXT_TIME).getState() != States.EMPTY);
    //System.out.printf("B----%d\n", possibleSpawn.size());
    if(possibleSpawn.size() > 0){
      Random rand = new Random();
      Cell spawn = possibleSpawn.get(rand.nextInt(possibleSpawn.size()));

      if(state.getState() == States.FISH){
        //System.out.printf("A----turns survived = %d\n", state.getTurnsSurvived());
        if(state.getTurnsSurvived() >= fishRoundsToBreed){
          //System.out.printf("B----MadeFish\n", possibleSpawn.size());
          spawn.setState(Cell.NEXT_TIME, new WaTorWorldState(States.FISH));
        }
      } else if(state.getState() == States.SHARK){
        if(state.getTurnsSurvived() >= sharkRoundsToBreed){
          spawn.setState(Cell.NEXT_TIME, new WaTorWorldState(States.SHARK));
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
    possibleFood.removeIf(food -> food.getState(Cell.NEXT_TIME).getState() == States.EMPTY);

    if(possibleFood.size() > 0){
      Random rand = new Random();
      Cell food = possibleFood.get(rand.nextInt(possibleFood.size()));
      food.setState(Cell.NEXT_TIME, new WaTorWorldState(States.EMPTY));
      return true;
    }
    return false;
  }

  private void foundFood(Cell cell){
    WaTorWorldState state = (WaTorWorldState) cell.getState(Cell.CURRENT_TIME);
    int roundsLasted = state.getTurnsSurvived();
    cell.setState(Cell.NEXT_TIME, new WaTorWorldState(States.SHARK, roundsLasted));
  }


  private void swapLogic(Cell currentCell, List<Cell> neighbors) {
    ArrayList<Cell> possibleSwaps = new ArrayList<>();
    possibleSwaps = getUsefulNeighbors(neighbors, WaTorWorldState.States.EMPTY);
    possibleSwaps
        .removeIf(neighbor -> neighbor.getState(Cell.NEXT_TIME).getState() != States.EMPTY);
    if(possibleSwaps.size() > 0){
      Random rand = new Random();
      Cell swapCell = possibleSwaps.get(rand.nextInt(possibleSwaps.size()));
      move(currentCell, swapCell);
    }
  }

  private void move(Cell a, Cell b){
    WaTorWorldState aAsWaTor = (WaTorWorldState) a.getState(Cell.CURRENT_TIME);
    WaTorWorldState.States aState = aAsWaTor.getState();
    int aTurnsSurvived = aAsWaTor.getTurnsSurvived();
    int aTurnsWithoutEating = aAsWaTor.getTurnsWithoutEating();

    b.setState(Cell.NEXT_TIME, new WaTorWorldState(aState, aTurnsSurvived, aTurnsWithoutEating));
    a.setState(Cell.NEXT_TIME, new WaTorWorldState());

  }

  /**
   * This method gets the specific rule set for the Wa Tor World variation, in the form of F<int>/S<int>/X<int>
   * where F is the number of rounds a fish needs to survive in order to breed
   * where S is the number of rounds a shark needs to survive in order to breed
   * where X is the number of round a shark will die after if it doesnt eat
   * @param params
   */
  public void setGameSpecifics(Map<String, String> params) {
    String rules = params.get("rules");
    String[] rulesSplit = rules.split("/");
    fishRoundsToBreed = Integer.valueOf(rulesSplit[0].substring(1));
    sharkRoundsToBreed = Integer.valueOf(rulesSplit[1].substring(1));
    sharkRoundsToStarve = Integer.valueOf(rulesSplit[2].substring(1));
  }
}
