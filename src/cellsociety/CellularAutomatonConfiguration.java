package cellsociety;

import cellsociety.model.CellGrid;
import cellsociety.model.CellState;
import cellsociety.model.CellularAutomatonRule;
import cellsociety.view.SimulationView;
import cellsociety.xml.XMLConfigurationParser;
import cellsociety.xml.XMLException;
import java.io.File;
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

  private CellState makeState(String simulationType, String contents) {
    try {
      return (CellState)cellsociety.model.states.Index.allStates
          .get(simulationType)
          .getConstructor(String.class)
          .newInstance(contents);
    } catch(Exception e) {
      return null;
    }
  }

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

  private void makeGrid(String simulationType, String gridType, List<List<String>> initialStates) throws XMLException {
    switch(gridType) {
      case "rectangular":
        RectangularCellGrid rectGrid = new RectangularCellGrid(gridWidth, gridHeight, gridWrapping, gridNeighbors);

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

        rectGrid.appendStates(initialState);

        grid = rectGrid;

        break;
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

  public String getGridType(){
    return gridType;
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

  public String getSimulationType(){
    return simulationType;
  }



}
