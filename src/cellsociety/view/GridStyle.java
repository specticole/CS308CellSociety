package cellsociety.view;

import java.util.List;
import java.util.Map;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * A GridStyle is an abstract collection of viewable cells laid out in a geometric pattern.
 * Extensions of this class will define the pattern using createGrid(), which takes in its 2D
 * dimensions, as well as if outlines are drawn. They will also be in charge of updating the
 * view based on changes sent from the controller. Each GridStyle has a reference to its parent
 * SimulationView in order to send information to the controller when a click is processed on the
 * grid.
 *
 * @author Bill Guo
 */

public abstract class GridStyle {
  public static final int VIEW_WIDTH = 250;
  public static final int VIEW_HEIGHT = 250;

  protected SimulationView simulationView;

  /**
   * Constructor for an implementation of a GridStyel
   * @param currentSimulationView parent SimulationView that the GridStyle is apart of
   */
  public GridStyle(SimulationView currentSimulationView){
    simulationView = currentSimulationView;
  }

  /**
   * Creates the Pane object that is added to the SimulationView.
   * Used by the SimulationView to show the grid on the UI.
   *
   * @param numCols number of columns specified by the config file
   * @param numRows number of rows specified by the config file
   * @param outlines whether or not outlines are drawn around each cell
   * @return Pane object to be added to the SimulationView main GridPane
   */
  public abstract Pane createGrid(int numCols, int numRows, boolean outlines);

  /**
   * Updates the grid to show the next set of states.
   * Used by SimulationView to send the information from the controller to the grid.
   *
   * @param listOfStates 2D list of the new states from the controller
   * @param stateToColor Map that converts a state to a specific color
   */
  public abstract void updateGrid(List<List<String>> listOfStates, Map<String, Color> stateToColor);

  /**
   * Handles the sending of information to the controller when a cell is clicked
   * @param colNumber column number of the cell
   * @param rowNumber row number of the cell
   */
  public void handleClick(int colNumber, int rowNumber){
    simulationView.changeCell(colNumber, rowNumber);
  }

}
