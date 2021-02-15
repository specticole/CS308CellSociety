package cellsociety.rules;

import cellsociety.Cell;
import cellsociety.CellGrid;
import cellsociety.CellularAutomatonRule;
import cellsociety.states.SegregationState;
import cellsociety.states.SegregationState.States;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SegregationRule extends CellularAutomatonRule {

  private double neighborsNeeded;


  public SegregationRule(Map<String, String> params) {
    super(params);
  }

  @Override
  public void advanceCellState(Cell cell, List<Cell> neighbors) {
    ArrayList<Cell> populatedNeighbors = getUsefulNeighbors(neighbors, States.OPEN, true);
    ArrayList<Cell> similarNeighbors = getUsefulNeighbors(populatedNeighbors,
        (States) cell.getState(Cell.CURRENT_TIME).getState(), false);

    if(needsToMove(populatedNeighbors, similarNeighbors)){
      move(cell);
    }

  }


  private void move(Cell currentCell){
    CellGrid cellGrid = currentCell.getParentGrid();
    ArrayList<Cell> openCells = new ArrayList<>();

    for(Cell cell : cellGrid){
      if(cell.getState(Cell.CURRENT_TIME).getState() == States.OPEN){
        if(cell.getState(Cell.NEXT_TIME).getState() == States.OPEN) {
          openCells.add(cell);
        }
      }
    }

    Random rand = new Random();
    Cell swapCell = openCells.get(rand.nextInt(openCells.size()));

    //currentCell.swapCells(swapCell);
  }

  private boolean needsToMove(List<Cell> populatedNeighbors, List<Cell> similarNeighbors){
    return (populatedNeighbors.size()/similarNeighbors.size() >= neighborsNeeded);
  }

  private ArrayList<Cell> getUsefulNeighbors(List<Cell> neighbors, SegregationState.States state, boolean different){
    ArrayList<Cell> usefulNeighbors = new ArrayList<>();
    for(Cell neighbor : neighbors){
      if((neighbor.getState(Cell.CURRENT_TIME).getState() == state) ^ different){
        usefulNeighbors.add(neighbor);
      }
    }
    return usefulNeighbors;
  }


  /**
   * This method gets the specific rule set for the Segregation variation, in the form of T<Double>
   * where T is the percentage of neighbors which need to be of the same type in order to not move
   * @param params
   */
  public void setGameSpecifics(Map<String, String> params) {
    String rules = params.get("rules");
    neighborsNeeded = Double.valueOf(rules.substring(1));
  }



}
