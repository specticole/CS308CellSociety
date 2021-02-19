package cellsociety;

import cellsociety.model.CellGrid;
import cellsociety.model.CellState;
import cellsociety.model.CellularAutomatonRule;
import cellsociety.xml.XMLConfigurationParser;
import cellsociety.xml.XMLException;
import java.io.File;
import java.util.List;
import java.util.Map;
import javafx.scene.paint.Color;

import cellsociety.model.states.*;
import cellsociety.model.grids.*;
import cellsociety.model.rules.*;

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
  private int gridWidth;
  private int gridHeight;
  private int gridNeighbors;
  private boolean gridWrapping;
  private Map<String, Color> cellStyles;
  private Map<String, String> simulationParameters;
  private List<List<String>> initialStates;

  /**
   * Stores relevant information given any XML file
   * @param configFile - XML configuration file
   */
  public CellularAutomatonConfiguration(File configFile) {
    XMLConfigurationParser docParser = new XMLConfigurationParser(configFile);
    parseXMLFile(docParser);
  }

  private void parseXMLFile(XMLConfigurationParser docParser) {
    simulationMetadata = docParser.getMetadata();
    gridWidth = docParser.getGridWidth();
    gridHeight = docParser.getGridHeight();
    gridNeighbors = docParser.getGridNeighbors();
    gridWrapping = docParser.getGridWrapping();
    cellStyles = docParser.getCellStyles();
    simulationParameters = docParser.getParameters();
    initialStates = docParser.getInitialStates();
    String gridType = docParser.getGridType();
    String simulationType = docParser.getSimulationType();
    makeGrid(simulationType, gridType, initialStates);
    makeRules(simulationType, simulationParameters);
  }

  private CellState makeState(String simulationType, String contents) {
    switch(simulationType) {
      case "gameoflife":
        return new GameOfLifeState(contents);
      case "percolation":
        return new PercolationState(contents);
      case "fire":
        return new FireState(contents);
      case "wator":
        return new WaTorWorldState(contents);
      case "segregation":
        return new SegregationState(contents);
      default:
        assert(false);
        return null;
    }
  }

  private void makeGrid(String simulationType, String gridType, List<List<String>> initialStates) {
    switch(gridType) {
      case "rectangular":
        grid = new RectangularCellGrid(gridWidth, gridHeight, gridWrapping, gridNeighbors);

        // populate our new grid
        CellState initialState[][] = new CellState[gridHeight][gridWidth];
        for(int y = 0; y < gridHeight; y++) {
          for(int x = 0; x < gridWidth; x++ ) {
            CellState state;
            try {
              state = makeState(simulationType, initialStates.get(y).get(x));
            }
            catch (IllegalArgumentException e) {
              throw new XMLException(e, "Invalid cell state specified");
            }
            initialState[y][x] = state;
            assert(state != null);
          }
        }

        ((Dense2DCellGrid)grid).appendStates(initialState);

        break;
    }
  }

  private void makeRules(String simulationType, Map<String, String> simulationParameters) {
    switch (simulationType) {
      case "gameoflife":
        ruleSet = new GameOfLifeRule(simulationParameters);
        break;
      case "percolation":
        ruleSet = new PercolationRule(simulationParameters);
        break;
      case "fire":
        ruleSet = new FireRule(simulationParameters);
        break;
      case "wator":
        ruleSet = new WaTorWorldRule(simulationParameters);
        break;
      case "segregation":
        ruleSet = new SegregationRule(simulationParameters);
        break;
      default:
        assert(false);
    }
  }

  // getters
  public CellularAutomatonRule getRuleSet() {
    return ruleSet;
  }

  public CellGrid getGrid() {
    return grid;
  }

  public Map<String, String> getSimulationMetadata() {
    return simulationMetadata;
  }

  public int getGridWidth() {
    return gridWidth;
  }

  public int getGridHeight() {
    return gridHeight;
  }

  /**
   * Returns mappings from cell states to colors for display
   *
   * @return - Map from Strings representing states to Color objects
   */
  public Map<String, Color> getCellStyles() {
    return cellStyles;
  }

  /**
   * Returns the initial configuration of states for display
   *
   * @return - 2D ArrayList of cell states as Strings
   */
  public List<List<String>> getInitialStates() {
    return initialStates;
  }

}
