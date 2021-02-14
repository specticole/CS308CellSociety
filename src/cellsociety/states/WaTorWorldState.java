package cellsociety.states;

import cellsociety.CellState;
import cellsociety.states.GameOfLifeState.States;




    import static java.lang.System.exit;

    import cellsociety.CellState;
    import java.util.*;
    import java.util.stream.Collectors;

public class WaTorWorldState extends CellState<WaTorWorldState.States> {

  public enum States {
    EMPTY,
    FISH,
    SHARK
  }

  public WaTorWorldState(States s) {
    super(s);
  }

  public WaTorWorldState(){
    this(States.EMPTY);
  }

  public WaTorWorldState(String str) {
    super(States.class, str);
  }
}
