package cellsociety.states;

import static java.lang.System.exit;

import cellsociety.CellState;
import java.util.*;
import java.util.stream.Collectors;

public class GameOfLifeState extends CellState<GameOfLifeState.States> {

  public enum States {
    DEAD,
    ALIVE
  }

  public GameOfLifeState(States s) {
    super(s);
  }

  public GameOfLifeState(){
    this(States.DEAD);
  }

  public GameOfLifeState(String str) {
    super(States.class, str);
  }
}
