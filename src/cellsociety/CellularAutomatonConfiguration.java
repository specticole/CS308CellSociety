package cellsociety;

import cellsociety.rules.GameOfLifeRule;
import cellsociety.rules.PercolationRules;
import cellsociety.xml.XMLParser;
import java.awt.Color;
import java.util.List;
import java.util.Map;

/**
 * This class stores all the information read in from the configuration file,
 * to be passed to the Model and View.
 *
 * @author Patrick Liu
 */
public class CellularAutomatonConfiguration {
  private CellStateRule ruleSet;
  private CellGrid grid;
  private Map<String, String> simulationMetadata;
  private int gridWidth;
  private int gridHeight;
  private Map<String, Color> cellStyles;
  private Map<String, String> simulationParameters;

  public CellularAutomatonConfiguration (String configFileName) {
    XMLParser docParser = new XMLParser(configFileName);
    simulationMetadata = docParser.getMetadata();
    gridWidth = docParser.getGridWidth();
    gridHeight = docParser.getGridHeight();
    cellStyles = docParser.getCellStyles();
    simulationParameters = docParser.getParameters();
    List<List<String>> initialStates = docParser.getInitialStates();
    String gridType = docParser.getGridType();
    String simulationType = docParser.getSimulationType();
    makeGrid(gridType, initialStates);
    makeRules(simulationType);
  }

  private void makeGrid (String gridType, List<List<String>> initialStates) {

  }

  private void makeRules (String simulationType) {
    switch (simulationType) {
      case "gameoflife":
        ruleSet = new GameOfLifeRule();
        break;
      case "percolation":
        ruleSet = new PercolationRules();
        break;
    }
  }

  // getters
  public CellStateRule getRuleSet() {
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

  public Map<String, Color> getCellStyles() {
    return cellStyles;
  }

}
