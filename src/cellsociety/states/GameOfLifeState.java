package cellsociety.states;

import static java.lang.System.exit;

import cellsociety.CellState;
import java.util.*;
import java.util.stream.Collectors;

public class GameOfLifeState extends CellState {

  private enum StateEnum {
    DEAD,
    ALIVE
  }

  private StateEnum state;

  private GameOfLifeState(StateEnum s) {
    this.state = s;
  }

  public GameOfLifeState(){
    this(StateEnum.DEAD);
  }

  public GameOfLifeState(String str) {
    this(StateEnum.valueOf(str));
  }

  /**
   * returns all possible states for this CA variation
   */
  @Override
  public Collection<String> getAvailableStates() {
    return super.enumToStringList(StateEnum.class);
  }

  /**
   *
   * @return the current state name as a string
   */
  @Override
  public String toString() {
    return state.toString();
  }
}
