package cellsociety;

/**
 * Top-level class of the cellular automaton model. A
 * CellularAutomaton consists of a CellGrid on which the simulation
 * takes place, and a CellStateRule that governs the evolution of the
 * CellGrid through time.
 *
 * A CellularAutomaton can then be advanced by calling step()
 * repeatedly.
 *
 * @author Franklin Wei
 */
public class CellularAutomaton {
  CellGrid grid;
  CellStateRule rule;

  /**
   * Create a new CellularAutomaton with the given initial grid and
   * rule.
   *
   * @param grid initial cell grid
   * @param rule initial rule
   */
  public CellularAutomaton(CellGrid grid, CellStateRule rule) {
    this.grid = grid;
    this.rule = rule;
  }

  /**
   * Change the rule used to govern evolution of subsequent
   * generations of this CellularAutomaton. Previous generations are
   * not affected.
   */
  public void setRule(CellStateRule rule) {
    this.rule = rule;
  }

  /**
   * Simulate one generation of the CellularAutomaton on `grid' by applying the `rule' to each Cell of the Grid.
   */
  public void step() {
    // First copy all cell states from time T to T + 1. This is
    // necessary so that cells can examine the state of cells that
    // have been "claimed" as movement destinations for other cells,
    // something that matters in games such as Wa-Tor World where cell
    // contents can "move" from one cell to the next.



    grid.advanceCurrentTime();
  }

  /**
   *
   */
  public CellGrid getGrid() {
    return grid;
  }
}
