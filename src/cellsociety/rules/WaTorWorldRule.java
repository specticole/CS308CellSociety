package cellsociety.rules;

import cellsociety.Cell;
import cellsociety.CellState;
import cellsociety.CellStateRule;
import cellsociety.states.WaTorWorldState;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class WaTorWorldRule implements CellStateRule {

  private int[] usedNeighbors = {1,3,4,6};

  @Override
  public void nextCellState(Cell cell, List<Cell> neighbors) {
    CellState cellState = cell.getState();
    if(cellState.getStateAsString().equals("FISH")){
      Cell swapCell = swapLogic(cell, neighbors);
      if (swapCell != null){
        cell.swapCells(swapCell);
      }
    } else if(cellState.getStateAsString().equals("SHARK")){
      Cell swapCell = swapLogic(cell, neighbors);
      if (swapCell != null){
        cell.swapCells(swapCell);
      }
    }
  }

  private Cell swapLogic(Cell currentCell, List<Cell> neighbors){
    int currentTime = currentCell.getParentGrid().getCurrentTime();
    ArrayList<Cell> possibleSwaps = new ArrayList<>();
    for(int neighborIndex : usedNeighbors){

      Cell neighbor = neighbors.get(neighborIndex);

      if(neighbor.getState().getStateAsString().equals("OPEN")){
        if(neighbor.getPriorStates().getLatestTime() == currentTime){
          possibleSwaps.add(neighbor);
        }
      }
    }
    if(possibleSwaps.size() == 0){
      return null;
    } else {
      Random rand = new Random();
      return possibleSwaps.get(rand.nextInt(possibleSwaps.size()));
    }
  }
}
