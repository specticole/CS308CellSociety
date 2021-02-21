package cellsociety.model.rules;

import java.util.*;

/**
 * Index containing all rules.
 */
public class Index {
  /**
   * Map of simulation type to rule class.
   */
  public static final Map<String, Class<? extends cellsociety.model.CellularAutomatonRule>> allRules =
      Map.of(
          "gameoflife", GameOfLifeRule.class,
          "segregation", SegregationRule.class,
          "wator", WaTorWorldRule.class,
          "fire", FireRule.class,
          "percolation", PercolationRule.class,
          "rps", RockPaperScissorRule.class
             );
}
