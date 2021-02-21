package cellsociety.model.rules;

import cellsociety.model.Cell;
import cellsociety.model.CellularAutomatonRule;
import cellsociety.model.states.RockPaperScissorState;
import cellsociety.model.states.RockPaperScissorState.States;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RockPaperScissorRule extends CellularAutomatonRule {

  private static final Map<States, List<States>> LOSSES_TO =
      Map.ofEntries(
        Map.entry(States.ROCK, Arrays.asList(States.PAPER)),
        Map.entry(States.PAPER, Arrays.asList(States.SCISSOR)),
        Map.entry(States.SCISSOR, Arrays.asList(States.ROCK))
  );

  public RockPaperScissorRule(Map<String, String> params) {

    super(params);
  }

  @Override
  public void advanceCellState(Cell cell, List<Cell> neighbors) {

  }
}
