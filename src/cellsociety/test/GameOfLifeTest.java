package cellsociety.test;

import cellsociety.*;
import cellsociety.grids.*;
import cellsociety.rules.*;
import cellsociety.states.*;

import java.util.concurrent.*;

public class GameOfLifeTest {
  private static void dumpState(CellState state[][]) {
    System.out.printf("State is %dx%d\n", state[0].length, state.length);
    for(int y = 0; y < state.length; y++) {
      for(int x = 0; x < state[y].length; x++) {
        String s = state[y][x].toString();

        System.out.printf("%s", s.charAt(0) == 'D' ? ' ' : s.charAt(0));
      }
      System.out.println("");
    }
  }

  public static void main(String args[]) {
    int w = 20, h = 10;
    RectangularCellGrid grid = new RectangularCellGrid(w, h,
                                                       true, 8);

    CellState initialState[][] = new GameOfLifeState[h][w];
    for(int y = 0; y < h; y++) {
      for(int x = 0; x < w; x++) {
        initialState[y][x] = new GameOfLifeState();
      }
    }

    initialState[0][1] = new GameOfLifeState("ALIVE");
    initialState[1][2] = new GameOfLifeState("ALIVE");
    initialState[2][2] = new GameOfLifeState("ALIVE");
    initialState[2][1] = new GameOfLifeState("ALIVE");
    initialState[2][0] = new GameOfLifeState("ALIVE");

    grid.appendStates(initialState);

    CellularAutomatonRule rule = new GameOfLifeRule(null);

    CellularAutomaton ca = new CellularAutomaton(grid, rule);

    for(;;) {
      System.out.printf("state at time=%d:\n", ca.getGrid().getCurrentTime());

      dumpState(((Dense2DCellGrid)ca.getGrid()).extractStates(0));
      try{
      TimeUnit.MILLISECONDS.sleep(100);
      } catch(Exception e) {
      }
      System.out.println("step");
      ca.step();
    }
  }
}
