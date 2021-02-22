package cellsociety.model.rules;

import cellsociety.model.Cell;
import cellsociety.model.CellularAutomatonRule;
import java.util.List;
import java.util.Map;

public class LangtonRule extends CellularAutomatonRule {

  /**
   * Construct a rule with a given parameter map.
   *
   * @param params Parameter map from String to String.
   */
  public LangtonRule(Map<String, String> params) {
    super(params);
  }

  @Override
  public void advanceCellState(Cell cell, List<Cell> neighbors) {

  }

  @Override
  public void setGameSpecifics(Map<String, String> params) {
  }
}
