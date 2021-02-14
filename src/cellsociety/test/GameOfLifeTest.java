package cellsociety.test;

import cellsociety.*;
import cellsociety.grids.*;
import cellsociety.rules.*;

public class GameOfLifeTest {
  public static void main(String args[]) {
    CellGrid grid = new RectangularCellGrid(20, 10,
                                            false, 8);

    CellularAutomatonRule rule = new GameOfLifeRule(null);

    CellularAutomaton ca = new CellularAutomaton(grid, rule);

    for(;;) {
      System.out.println("step");
      ca.step();
    }
  }
}
