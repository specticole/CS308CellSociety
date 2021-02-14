package cellsociety;

import cellsociety.rules.GameOfLifeRule;
import cellsociety.rules.PercolationRule;
import cellsociety.xml.XMLParser;
import java.util.List;
import java.util.Map;
import javafx.scene.paint.Color;

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
  private Map<String, Color> cellStyles;
  private Map<String, String> simulationParameters;
  private List<List<String>> initialStates;

  /**
   * Parses through configuration file and stores relevant information
   *
   * @param configFileName - String filename for XML configuration file
   */
  public CellularAutomatonConfiguration(String configFileName) {
    XMLParser docParser = new XMLParser(configFileName);
    simulationMetadata = docParser.getMetadata();
    gridWidth = docParser.getGridWidth();
    gridHeight = docParser.getGridHeight();
    cellStyles = docParser.getCellStyles();
    simulationParameters = docParser.getParameters();
    initialStates = docParser.getInitialStates();
    String gridType = docParser.getGridType();
    String simulationType = docParser.getSimulationType();
    makeGrid(gridType, initialStates);
    makeRules(simulationType, simulationParameters);
  }

  private void makeGrid(String gridType, List<List<String>> initialStates) {

  }

  private void makeRules(String simulationType, Map<String, String> simulationParameters) {
    switch (simulationType) {
      case "gameoflife":
        ruleSet = new GameOfLifeRule(simulationParameters);
        break;
      case "percolation":
        ruleSet = new PercolationRule(simulationParameters);
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

  public Map<String, String> getSimulationParameters() {
    return simulationParameters;
  }

}
