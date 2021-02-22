package cellsociety.model.rules;

import cellsociety.model.*;
import cellsociety.model.states.ElementaryState;
import cellsociety.model.states.ElementaryState.*;

import java.util.*;
import java.util.stream.*;

/**
 * Implementation of a 1-D elementary cellular automaton on a 2D grid,
 * with time increasing along the Y axis.
 */
public class ElementaryRule extends CellularAutomatonRule {
  /**
   * Rule as a Wolfram code (see
   * https://en.wikipedia.org/wiki/Wolfram_code).
   */
  private int rule;

  /**
   * Direction in which time increases. Must be a basis vector
   * (i.e. length 1 along an axis). Currently +Y.
   */
  private static final GridCoordinates timeDirection = new GridCoordinates(0, 1);
  private static final GridCoordinates indexDirection = new GridCoordinates(1, 0);

  /**
   * Construct a rule with the given parameter map.
   *
   * @param params Parameter map.
   */
  public ElementaryRule(Map<String, String> params) {
    super(params);

    setGameSpecifics(params);
  }

  private GridCoordinates getDelta(Cell center, Cell other) {
    return other.getCoordinates().subtract(center.getCoordinates());
  }

  /**
   * This method is called every step, and updates the cell's state under certain conditions
   *
   * @param cell this is the cell which's state will be updated
   * @param neighbors this is all 8 neighbors of cell
   */
  @Override
  public void advanceCellState(Cell center, List<Cell> neighbors) {
    if(center.getState(Cell.CURRENT_TIME).getState() == States.ALIVE)
      return; // preserve this state

    neighbors = neighbors.stream()
                .filter(other -> getDelta(center, other).dot(timeDirection) < 0)
                .sorted((o1, o2) -> getDelta(center, o1).dot(indexDirection) < getDelta(center, o2).dot(indexDirection) ? -1 : 1)
                .collect(Collectors.toList());

    int state = 0;
    for(int i = 0; i < neighbors.size(); i++)
      state = (state << 1) | (neighbors.get(i).getState(Cell.CURRENT_TIME).getState() == States.ALIVE ? 1 : 0);

    center.setState(Cell.NEXT_TIME, new ElementaryState((rule >> state & 1) == 1 ? States.ALIVE : States.DEAD));
  }

  /**
   * This method gets the specific rule set for the game of live
   * variation, in the form of rule=CODE, where CODE is the Wolfram
   * code corresponding to the rule.
   *
   * @param params Parameter map, with key "rule=CODE".
   */
  @Override
  public void setGameSpecifics(Map<String, String> params) {
    rule = 30;

    if(params.containsKey("rule")) {
      int paramRule = Integer.parseInt(params.get("rule"));

      if(0 <= paramRule && paramRule <= 0xff)
        rule = paramRule;
    }
  }
}
