package cellsociety.model.rules;

import cellsociety.model.Cell;
import cellsociety.model.CellGrid;
import cellsociety.model.CellularAutomatonRule;
import cellsociety.model.states.SegregationState;
import cellsociety.model.states.SegregationState.States;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
 * String threshold = "0.2";
 * String rules = "neighborsNeeded";
 * params.set(rules, threshold);
 * SegregationRule segregationRule = new SegregationRule(params);
 * grid.copyState();
 *
 * for(Cell c : grid)
 * segregationRule.advanceCellState(c, grid.getNeighbors(c));
 *
 * grid.advanceCurrentTime();
 *
 */
public class SegregationRule extends CellularAutomatonRule {

  private double neighborsNeeded;


  /**
   * Construct a SegregationRule with the given parameter map.
   *
   * @param params Parameter map.
   */
  public SegregationRule(Map<String, String> params) {
    super(params);
    setGameSpecifics(params);
  }

  @Override
  public void advanceCellState(Cell cell, List<Cell> neighbors) {
    ArrayList<Cell> populatedNeighbors = getPopulatedNeighbors(neighbors);
    ArrayList<Cell> similarNeighbors = getSimilarNeighbors(neighbors,
        (States) cell.getState(Cell.CURRENT_TIME).getState());

    if (cell.getState(Cell.CURRENT_TIME).getState() != States.OPEN &&
        needsToMove(populatedNeighbors, similarNeighbors))
      move(cell);
  }

  private ArrayList<Cell> getSimilarNeighbors(List<Cell> neighbors, SegregationState.States state){
    ArrayList<Cell> similarNeighbors = new ArrayList<>();
    for(Cell neighbor : neighbors){
      if(neighbor.getState(Cell.CURRENT_TIME).getState() == state){
        similarNeighbors.add(neighbor);
      }
    }
    return similarNeighbors;
  }


  private ArrayList<Cell> getPopulatedNeighbors(List<Cell> neighbors){
    ArrayList<Cell> populatedNeighbors = new ArrayList<>();
    for(Cell neighbor : neighbors){
      if(neighbor.getState(Cell.CURRENT_TIME).getState() == States.X || neighbor.getState(Cell.CURRENT_TIME).getState() == States.O){
        populatedNeighbors.add(neighbor);
      }
    }
    return populatedNeighbors;
  }


  private void move(Cell currentCell){
    CellGrid cellGrid = currentCell.getParentGrid();
    ArrayList<Cell> openCells = new ArrayList<>();

    for(Cell cell : cellGrid) {
      if(cell.getState(Cell.CURRENT_TIME).getState() == States.OPEN &&
         cell.getState(Cell.NEXT_TIME).getState() == States.OPEN)
          openCells.add(cell);
    }

    System.out.printf("----%d open cells\n", openCells.size());

    if(openCells.size() == 0){
      return;
    }

    Random rand = new Random();
    Cell swapCell = openCells.get(rand.nextInt(openCells.size()));

    swap(currentCell, swapCell);
  }

  private void swap(Cell a, Cell b){
    b.setState(Cell.NEXT_TIME, new SegregationState(((SegregationState) a.getState(Cell.CURRENT_TIME)).getState()));
    a.setState(Cell.NEXT_TIME, new SegregationState());
  }

  private boolean needsToMove(List<Cell> populatedNeighbors, List<Cell> similarNeighbors){
    if(similarNeighbors.size() == 0){
      return (populatedNeighbors.size() != 0);
    }
    return ((double) populatedNeighbors.size()/(double) similarNeighbors.size() >= neighborsNeeded);
  }

  /**
   * This method gets the specific rule set for the Segregation
   * variation, in the form of (Double) where the double is the
   * percentage of neighbors which need to be of the same type in
   * order to not move
   *
   * @param params Parameter map.
   */
  @Override
  public void setGameSpecifics(Map<String, String> params) {
    neighborsNeeded = 0.2; // default value
    if (params.containsKey("neighborsNeeded")) {
      double paramNeeded = 0.0;
      try {
        paramNeeded = Double.parseDouble(params.get("neighborsNeeded"));
      }
      catch (Exception ignored) {

      }
      if (paramNeeded > 0 && paramNeeded < 1) {
        neighborsNeeded = paramNeeded;
      }
    }
  }



}
