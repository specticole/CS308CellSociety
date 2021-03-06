package cellsociety.model.rules;

import cellsociety.model.Cell;
import cellsociety.model.CellState;
import cellsociety.model.CellularAutomatonRule;
import cellsociety.model.states.WaTorWorldState;
import cellsociety.model.states.WaTorWorldState.*;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 *
 * @author Cole Spector
 * @author Franklin Wei
 *
 *
 * Implementation of CellularAutomatonRule for Wa-Tor-World simulation.
 * This class will be called for every step of the simulation, and is used
 * to update its WaTorWorldState accordingly.
 *
 * This method assumes that it is passed a Map, with a key: "rules" ->
 * a string "F(int)/S(int)/X(int)"
 *
 * This class relies on Cell.java and WaTorWorldRule.java
 *
 * Example:
 *
 * CellGrid grid = new CellGrid(...);
 *
 * Map<String, String> params = new Map();
 * String threshold = "0.2";
 * String rules = "neighborsNeeded";
 * params.set(rules, threshold);
 * WaTorWorldRule waTorWorldRule = new WaTorWorldRule(params);
 * grid.copyState();
 *
 * for(Cell c : grid)
 * waTorWorldRule.advanceCellState(c, grid.getNeighbors(c));
 *
 * grid.advanceCurrentTime();
 *
 */
public class WaTorWorldRule extends CellularAutomatonRule {
  private int sharkRoundsToBreed;
  private int sharkRoundsToStarve;
  private int fishRoundsToBreed;

  /**
   * Construct a rule with given parameters.
   *
   * @param params Parameter map.
   */
  public WaTorWorldRule(Map<String, String> params) {
    super(params);
    setGameSpecifics(params);
  }

  @Override
  public void advanceCellState(Cell cell, List<Cell> neighbors) {

    boolean moved = false, died = false;

    switch((States)cell.getState(Cell.CURRENT_TIME).getState()) {
      case FISH:
        moved = tryMoving(cell, neighbors);
        break;

      case SHARK:
        if(eatFish(neighbors)){
          foundFood(cell);
        } else {
          died = starve(cell);
          if(!died)
            moved = tryMoving(cell, neighbors);
        }
        break;

      case EMPTY:
        return; // do nothing
    }

    // If we didn't move, update the survived turns count in our next
    // state (if we moved, that's handled by swapStates).
    if(!moved)
    {
      System.out.println("no move");
      if(!died)
        updateTurnsSurvived(cell);
    } else {
      System.out.println("moved");

    }

    if(!died)
      breed(cell.getState(Cell.CURRENT_TIME), neighbors);
  }


  private boolean starve(Cell cell){
    WaTorWorldState state = (WaTorWorldState) cell.getState(Cell.CURRENT_TIME);
    System.out.printf("----%d turns without eating\n", state.getTurnsWithoutEating());
    if(state.getTurnsWithoutEating() >= sharkRoundsToStarve){
      System.out.println("starving");
      cell.setState(Cell.NEXT_TIME, new WaTorWorldState(States.EMPTY));
      return true;
    }
    return false;
  }

  private void updateTurnsSurvived(Cell cell){
    WaTorWorldState currentState = (WaTorWorldState) cell.getState(Cell.CURRENT_TIME);
    int newTurnsSurvived = currentState.getTurnsSurvived() + 1;
    int newTurnsWithoutEating = currentState.getTurnsWithoutEating() + 1;
    cell.setState(Cell.NEXT_TIME, new WaTorWorldState(currentState.getState(), newTurnsSurvived, newTurnsWithoutEating));
  }

  private void breed(CellState cellState, List<Cell> neighbors){
    WaTorWorldState state = (WaTorWorldState) cellState;
    List<Cell> possibleSpawn = filterNeighborsByState(neighbors, States.EMPTY);

    if(!possibleSpawn.isEmpty()){
      Random rand = new Random();
      Cell spawn = possibleSpawn.get(rand.nextInt(possibleSpawn.size()));

      if(state.getState() == States.FISH){
        //System.out.printf("A----turns survived = %d\n", state.getTurnsSurvived());
        //System.out.printf("B----MadeFish\n", possibleSpawn.size());
        if(state.getTurnsSurvived() == fishRoundsToBreed){
          spawn.setState(Cell.NEXT_TIME, new WaTorWorldState(States.FISH));
        }
      } else if(state.getState() == States.SHARK &&
                state.getTurnsSurvived() == sharkRoundsToBreed) {
        spawn.setState(Cell.NEXT_TIME, new WaTorWorldState(States.SHARK));
      }
    }
  }
  /**
   * Filter a list of Cells for those matching `state' at both the
   * current and next time steps.
   */
  private List<Cell> filterNeighborsByState(List<Cell> neighbors, States state){
    return neighbors.stream()
        .filter(cell -> cell.getState(Cell.CURRENT_TIME).getState() == state)
        .filter(cell -> cell.getState(Cell.NEXT_TIME).getState() == state)
        .collect(Collectors.toList());
  }

  private boolean eatFish(List<Cell> neighbors){
    // Find cells which currently have fish, and have not been claimed
    // by another shark for eating (i.e. are still present at the next
    // time step).
    List<Cell> possibleFood = filterNeighborsByState(neighbors, States.FISH);

    if(!possibleFood.isEmpty()){
      Random rand = new Random();
      Cell food = possibleFood.get(rand.nextInt(possibleFood.size()));
      food.setState(Cell.NEXT_TIME, new WaTorWorldState(States.EMPTY));
      System.out.println("Eat fish");
      return true;
    }
    return false;
  }

  private void foundFood(Cell cell){
    WaTorWorldState state = (WaTorWorldState) cell.getState(Cell.CURRENT_TIME);
    int roundsLasted = state.getTurnsSurvived();
    cell.setState(Cell.NEXT_TIME, new WaTorWorldState(States.SHARK, roundsLasted));
  }


  private boolean tryMoving(Cell currentCell, List<Cell> neighbors) {
    List<Cell> possibleSwaps = filterNeighborsByState(neighbors, States.EMPTY);

    if(!possibleSwaps.isEmpty()){
      Random rand = new Random();
      Cell swapCell = possibleSwaps.get(rand.nextInt(possibleSwaps.size()));
      swapStates(currentCell, swapCell);
      return true;
    }

    return false;
  }

  private void swapStates(Cell a, Cell b){
    WaTorWorldState aAsWaTor = (WaTorWorldState) a.getState(Cell.CURRENT_TIME);
    WaTorWorldState.States aState = aAsWaTor.getState();
    int aTurnsSurvived = aAsWaTor.getTurnsSurvived() + 1;
    int aTurnsWithoutEating = aAsWaTor.getTurnsWithoutEating() + 1;

    b.setState(Cell.NEXT_TIME, new WaTorWorldState(aState, aTurnsSurvived, aTurnsWithoutEating));
    a.setState(Cell.NEXT_TIME, new WaTorWorldState());
  }

  /**
   * This method gets the specific rule set for the Wa Tor World
   * variation, in the form of F(int)/S(int)/X(int) where F is the
   * number of rounds a fish needs to survive in order to breed where
   * S is the number of rounds a shark needs to survive in order to
   * breed where X is the number of round a shark will die after if it
   * doesnt eat
   *
   * @param params Parameter map containing a subset of the allowed
   * key-value pairs.
   */
  @Override
  public void setGameSpecifics(Map<String, String> params) {
    fishRoundsToBreed = 3; // default value
    sharkRoundsToBreed = 5; // default value
    sharkRoundsToStarve = 3; // default value
    if (params.containsKey("rules")) {
      String rules = params.get("rules");
      String[] rulesSplit = rules.split("/");
      if (rulesSplit.length == 3 && rulesSplit[0].startsWith("F") && rulesSplit[1].startsWith("S") && rulesSplit[2].startsWith("X")) {
        try {
          if (Integer.parseInt(rulesSplit[0].substring(1)) > 0) {
            fishRoundsToBreed = Integer.parseInt(rulesSplit[0].substring(1));
          }
          if (Integer.parseInt(rulesSplit[1].substring(1)) > 0) {
            sharkRoundsToBreed = Integer.parseInt(rulesSplit[1].substring(1));
          }
          if (Integer.parseInt(rulesSplit[2].substring(1)) > 0) {
            sharkRoundsToStarve = Integer.parseInt(rulesSplit[2].substring(1));
          }
        }
        catch (Exception ignored) {

        }
      }
    }
  }
}
