package cellsociety.model;

/**
 * Top-level class of the cellular automaton model. A
 * CellularAutomaton consists of a CellGrid on which the simulation
 * takes place, and a CellularAutomatonRule that governs the evolution
 * of the CellGrid through time.
 *
 * A CellularAutomaton can be advanced through time by calling the
 * step() method repeatedly.
 *
 * The state (i.e. grid) of the cellular automaton can be retrieved
 * through getGrid(), which contains the current and past states of
 * the cellular automaton.
 *
 * @author Franklin Wei
 */
public class CellularAutomaton {
  private CellGrid grid;
  private CellularAutomatonRule rule;

  /**
   * Create a new CellularAutomaton with the given initial grid and
   * rule.
   *
   * @param grid initial cell grid
   * @param rule initial rule
   */
  public CellularAutomaton(CellGrid grid, CellularAutomatonRule rule) {
    this.grid = grid;
    this.rule = rule;
  }

  /**
   * Change the rule used to govern evolution of subsequent
   * generations of this CellularAutomaton. Previous generations are
   * not affected.
   *
   * @param rule New rule.
   */
  public void setRule(CellularAutomatonRule rule) {
    this.rule = rule;
  }

  /**
   * Simulate one generation of the CellularAutomaton on `grid' by
   * applying the `rule' to each Cell of the Grid.
   */
  public void step() {
    // First copy all cell states from time T to T + 1. This is
    // necessary so that cells can examine the state of cells that
    // have been "claimed" as movement destinations for other cells,
    // something that matters in games such as Wa-Tor World where cell
    // contents can "move" from one cell to the next, and we need to
    // ensure that no two cells try to "move" to the same location.
    grid.copyState();

    for(Cell c : grid)
      rule.advanceCellState(c, grid.getNeighbors(c));

    grid.advanceCurrentTime();
  }

  /**
   * Retrieve the grid, which contains all state.
   *
   * @return grid
   */
  public CellGrid getGrid() {
    return grid;
  }
}
