package cellsociety.model.states;

import java.util.*;

/**
 * Index containing all states.
 */
public class Index {
  /**
   * Map of simulation type to state class.
   */
  public static final Map<String, Class<? extends cellsociety.model.CellState>> allStates =
      Map.of(
          "gameoflife", GameOfLifeState.class,
          "segregation", SegregationState.class,
          "wator", WaTorWorldState.class,
          "fire", FireState.class,
          "percolation", PercolationState.class,
          "rps", RockPaperScissorState.class,
          "elementary", ElementaryState.class
             );
}
