package cellsociety.model;

import java.util.List;
import java.util.Map;

/**
 * Abstract type representing a rule governing the time evolution of a
 * CellularAutomaton.
 *
 * @author Cole Spector
 * @author Franklin Wei
 */
public abstract class CellularAutomatonRule {
  /**
   * Map from parameter name to parameter value for rule-specific
   * parameters.
   */
  protected Map<String, String> parameters;

  /**
   * Construct a rule with a given parameter map.
   *
   * @param params Parameter map from String to String.
   */
  public CellularAutomatonRule(Map<String, String> params) {
    this.parameters = params;
  }

  /**
   * Advance the state of a cell. This is called once per simulation
   * timestep for every Cell in the CellGrid.
   *
   * @param cell Cell to update.
   * @param neighbors List of neighboring Cells.
   */
  public abstract void advanceCellState(Cell cell, List<Cell> neighbors);

  /**
   * Set the simulation parameters. This is called when the simulation is
   * first set up using values read from the configuration file or default values.
   * This method is also called when the user inputs new parameters into the GUI.
   *
   * @param params - map of parameter names and values
   */
  public abstract void setGameSpecifics(Map<String, String> params);

}
