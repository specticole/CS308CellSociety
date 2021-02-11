package cellsociety;

/**
 *
 *
 * @author Franklin Wei
 */
public class CellularAutomaton {
  CellGrid myGrid;
  CellStateRule myRule;

  /**
   *
   */
  public CellularAutomaton(CellGrid grid, CellStateRule rule) {
    myGrid = grid;
    myRule = rule;
  }

  /**
   *
   */
  public void setRule(CellStateRule rule) {
    myRule = rule;
  }

  /**
   *
   */
  public void step() {
  }

  /**
   *
   */
  public CellGrid getGrid() {
    return myGrid;
  }
}
