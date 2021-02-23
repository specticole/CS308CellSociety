package cellsociety;

import cellsociety.model.CellGrid;
import cellsociety.model.CellState;
import cellsociety.model.CellularAutomatonRule;
import cellsociety.view.SimulationView;
import cellsociety.xml.XMLConfigurationParser;
import cellsociety.xml.XMLException;
import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javafx.scene.paint.Color;

import cellsociety.model.grids.*;

/**
 * This class stores all the information read in from the configuration file, to be passed to the
 * Model and View.
 *
 * @author Patrick Liu
 */
public class CellularAutomatonConfiguration {

  private CellularAutomatonRule ruleSet;
  private CellGrid grid;
  private Map<String, String> simulationMetadata;
  private String gridType;
  private int gridWidth;
  private int gridHeight;
  private int gridNeighbors;
  private boolean gridWrapping;
  private Map<String, Color> cellStyles;
  private Map<String, String> simulationParameters;
  private List<List<String>> initialStates;
  private String simulationType;

  /**
   * Stores relevant information given any XML file
   * @param configFile - XML configuration file
   */
  public CellularAutomatonConfiguration(File configFile) throws XMLException {
    XMLConfigurationParser docParser = new XMLConfigurationParser(configFile);
    parseXMLFile(docParser);
  }

  private void parseXMLFile(XMLConfigurationParser docParser) throws XMLException {
    simulationMetadata = docParser.getMetadata();
    gridWidth = docParser.getGridWidth();
    gridHeight = docParser.getGridHeight();
    gridNeighbors = docParser.getGridNeighbors();
    gridWrapping = docParser.getGridWrapping();
    cellStyles = docParser.getCellStyles();
    simulationParameters = docParser.getParameters();
    initialStates = docParser.getInitialStates();
    gridType = docParser.getGridType();
    simulationType = docParser.getSimulationType();
    makeGrid(simulationType, gridType, initialStates);
    makeRules(simulationType, simulationParameters);
  }

  // returns subclass of CellState using reflection
  private CellState makeState(String simulationType, String contents) {
    try {
      return cellsociety.model.states.Index.allStates
          .get(simulationType)
          .getConstructor(String.class)
          .newInstance(contents);
    } catch(Exception e) {
      return null;
    }
  }

  // returns subclass of CellularAutomatonRule using reflection
  private void makeRules(String simulationType, Map<String, String> simulationParameters) throws XMLException {
    try {
      ruleSet = (CellularAutomatonRule)cellsociety.model.rules.Index.allRules
          .get(simulationType)
          .getConstructor(Map.class)
          .newInstance(simulationParameters);
    } catch(Exception e) {
      throw new XMLException(new IllegalArgumentException());
    }
  }

  // initialize grid using values read in from configuration file
  private void makeGrid(String simulationType, String gridType, List<List<String>> initialStates) throws XMLException {
    switch(gridType) {
      case "rectangular":
      case "hexagonal":
        Dense2DCellGrid denseGrid = null;

        if(gridType.equals("rectangular"))
          denseGrid = new RectangularCellGrid(gridWidth, gridHeight, gridWrapping, gridNeighbors);
        else if(gridType.equals("hexagonal"))
          denseGrid = new HexagonalCellGrid(gridWidth, gridHeight, gridWrapping);

        // populate our new grid
        CellState initialState[][] = new CellState[gridHeight][gridWidth];
        for(int y = 0; y < gridHeight; y++) {
          for(int x = 0; x < gridWidth; x++ ) {
            CellState state = makeState(simulationType, initialStates.get(y).get(x));
            if (state != null) {
              initialState[y][x] = state;
            }
            else {
              throw new XMLException(new IllegalArgumentException());
            }
          }
        }

        denseGrid.appendStates(initialState);

        grid = denseGrid;

        break;
    }
  }

  /**
   * Returns the rule set
   * @return - a subclass of CellularAutomatonRule for the specified simulation
   */
  public CellularAutomatonRule getRuleSet() {
    return ruleSet;
  }

  /**
   * Returns the grid object tied to the current simulation
   * @return - a grid that holds the Cells for the current simulation
   */
  public CellGrid getGrid() {
    return grid;
  }

  /**
   * Returns read-only copy of the metadata
   * @return - unmodifiable map of metadata names and values
   */
  public Map<String, String> getSimulationMetadata() {
    return Collections.unmodifiableMap(simulationMetadata);
  }

  /**
   * Returns the grid type
   * @return - the name of the grid type
   */
  public String getGridType(){
    return gridType;
  }

  /**
   * Returns the width of the grid in cells
   * @return - the number of cells per row
   */
  public int getGridWidth() {
    return gridWidth;
  }

  /**
   * Returns the height of the grid in cells
   * @return - the number of cells per column
   */
  public int getGridHeight() {
    return gridHeight;
  }

  /**
   * Returns read-only mappings from cell states to colors for display
   *
   * @return - unmodifiable map from Strings representing states to Color objects
   */
  public Map<String, Color> getCellStyles() {
    return Collections.unmodifiableMap(cellStyles);
  }

  /**
   * Returns the initial configuration of states for display. This list
   * is not read-only, since it is continuously updated in the Controller.
   *
   * @return - 2D ArrayList of cell states as Strings
   */
  public List<List<String>> getInitialStates() {
    return initialStates;
  }

  /**
   * Returns the simulation type
   * @return - the name of the simulation type
   */
  public String getSimulationType(){
    return simulationType;
  }

  /**
   * Returns read-only map of the simulation-specific parameters
   * @return - unmodifiable map of parameter names and values
   */
  public Map<String, String> getSimulationParameters(){
    return Collections.unmodifiableMap(simulationParameters);
  }



}
