package cellsociety.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.paint.Color;
import java.util.HashMap;
import java.util.Map;

import static java.util.Map.entry;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


/**
 * This class handles parsing XML files and returning appropriately-formatted Objects for given
 * input tags. Heavily based on code from class.
 *
 * @author Rhondu Smithwick
 * @author Robert C. Duvall
 * @author Patrick Liu
 */
public class XMLConfigurationParser extends XMLGenericParser {

  private static final List<String> META_FIELDS = new ArrayList<>(
      Arrays.asList("title", "author", "description"));
  private static final List<String> SUPPORTED_SIMULATIONS = new ArrayList<>(
      Arrays.asList("fire", "gameoflife", "percolation",
          "segregation",
          "wator"));
  private static final List<String> SUPPORTED_GRID_TYPES = new ArrayList<>(
      Arrays.asList("rectangular"));
  private static final Map<String, ArrayList<Integer>> SUPPORTED_GRIDS = Map.ofEntries(
      entry("rectangular", new ArrayList<>(Arrays.asList(4, 8)))
  );

  /**
   * Create parser for any XML file input
   *
   * @param file - XML configuration file
   * @throws XMLException - if parser is unable to be created
   */
  public XMLConfigurationParser(File file) throws XMLException {
    super(file);
  }

  /**
   * Returns map containing metadata: title, author, and description of simulation
   *
   * @return - map of metadata, with keys of "title," "author," and "description"
   */
  public Map<String, String> getMetadata() {
    Map<String, String> simulationMetadata = new HashMap<>();
    Element metaElement = getElement(root, "meta");
    for (String field : META_FIELDS) {
      simulationMetadata.put(field, getTextValue(metaElement, field));
    }
    return simulationMetadata;
  }

  /**
   * Returns type of simulation in configuration file, if supported
   *
   * @return - String representing simulation type or error if type is not supported
   * @throws XMLException - if simulation type is not found in SUPPORTED_SIMULATIONS
   */
  public String getSimulationType() throws XMLException {
    if (! root.getNodeName().equals("simulation")) {
      throw new XMLException(new IllegalArgumentException(), "Root node must be named 'simulation'");
    }
    String simulationType = getCurrentAttribute(root, "rules").toLowerCase();
    if (SUPPORTED_SIMULATIONS.contains(simulationType)) {
      return simulationType;
    } else {
      throw new XMLException(new IllegalArgumentException(),
          simulationType + " is not a supported simulation type");
    }
  }

  /**
   * Returns type of grid in configuration file, if supported
   *
   * @return - String representing grid type or error if type is not supported
   * @throws XMLException - if grid type is not found in SUPPORTED_GRID_TYPES
   */
  public String getGridType() throws XMLException {
    String gridType = getAttribute(root, "grid", "type").toLowerCase();
    if (SUPPORTED_GRIDS.keySet().contains(gridType)) {
      return gridType;
    } else {
      throw new XMLException(new IllegalArgumentException(),
          gridType + " is not a supported grid type");
    }
  }

  /**
   * Returns width of grid in cells
   *
   * @return - number of cells in a row of the grid, or an error if the width is invalid
   * @throws XMLException - if grid width is not an integer or is less than 1
   */
  public int getGridWidth() throws XMLException {
    int gridWidth;
    try {
      gridWidth = Integer.valueOf(getAttribute(root, "grid", "width"));
    } catch (IllegalArgumentException e) {
      throw new XMLException(e, "Grid width must be an integer");
    }
    if (gridWidth > 0) {
      return gridWidth;
    } else {
      throw new XMLException(new IllegalArgumentException(), "Grid width must be greater than 0");
    }
  }

  /**
   * Returns height of grid in cells
   *
   * @return - number of cells in a column of the grid, or an error if the height is invalid
   * @throws XMLException - if grid height is not an integer or is less than 1
   */
  public int getGridHeight() {
    int gridHeight;
    try {
      gridHeight = Integer.valueOf(getAttribute(root, "grid", "height"));
    } catch (IllegalArgumentException e) {
      throw new XMLException(e, "Grid height must be an integer");
    }
    if (gridHeight > 0) {
      return gridHeight;
    } else {
      throw new XMLException(new IllegalArgumentException(), "Grid height must be greater than 0");
    }
  }

  /**
   * Returns number of neighbors each cell has
   *
   * @return - number of neighbors that should be counted for each cell, or an error
   * @throws XMLException - if number is not an integer or is not supported for the grid type
   */
  public int getGridNeighbors() throws XMLException {
    int gridNeighbors;
    try {
      gridNeighbors = Integer.valueOf(getAttribute(root, "grid", "neighbors"));
    } catch (IllegalArgumentException e) {
      throw new XMLException(e, "Neighbors must be an integer");
    }
    if (SUPPORTED_GRIDS.get(getGridType()).contains(gridNeighbors)) {
      return gridNeighbors;
    } else {
      throw new XMLException(new IllegalArgumentException(), "Number of neighbors not supported");
    }
  }

  /**
   * Returns whether the grid should wrap around in a toroidal fashion
   *
   * @return - boolean indicating whether the grid should wrap, or an error
   * @throws XMLException - if attribute value is not "true" or "false"
   */
  public boolean getGridWrapping() throws XMLException {
    try {
      return Boolean.valueOf(getAttribute("grid", "wrapping"));
    } catch (IllegalArgumentException e) {
      throw new XMLException(e, "Wrapping attribute must be true or false");
    }
  }

  /**
   * Generates initial configuration of states depending on format specified in configuration file
   *
   * @return - 2D List of cell states represented as Strings
   */
  public List<List<String>> getInitialStates() {
    Element gridElement = getElement(root, "grid");
    if (gridElement.hasAttribute("distribution")) {
      switch (getAttribute(root, "grid", "distribution").toLowerCase()) {
        case "specified":
          return makeSpecifiedInitialStates();
        case "randomspecified":
          break; // todo: randomly pick state at specified locations, with or without desired distribution
        case "randomtotal":
          return makeRandomTotalInitialStates(); // todo: randomly pick state based on total locations to occupy, with or without desired distribution
      }
    }
    return makeSpecifiedInitialStates();
  }

  private List<List<String>> makeSpecifiedInitialStates() {
    List<List<String>> gridInitialStates = new ArrayList<>();
    Element gridElement = getElement(root, "grid");
    NodeList gridList = getNodes(gridElement, "gridrow");
    for (int row = 0; row < gridList.getLength(); row++) {
      Element rowElement = (Element) gridList.item(row);
      NodeList rowList = getNodes(rowElement, "gridcell");
      ArrayList<String> rowInitialStates = new ArrayList<>();
      for (int col = 0; col < rowList.getLength(); col++) {
        Element cellElement = (Element) rowList.item(col);
        rowInitialStates.add(cellElement.getTextContent());
      }
      gridInitialStates.add(rowInitialStates);
    }
    return gridInitialStates;
  }

  private List<List<String>> makeRandomTotalInitialStates() {
    List<List<String>> gridInitialStates = new ArrayList<>();
    List<String> possibleCellStates = getCellStates();
    if (root.getElementsByTagName("distribution").getLength() > 0) {
      possibleCellStates = getCellStateDistribution();
    }
    for (int row = 0; row < getGridHeight(); row++) {
      ArrayList<String> rowInitialStates = new ArrayList<>();
      for (int col = 0; col < getGridWidth(); col++) {
        rowInitialStates
            .add(possibleCellStates.get((int) (Math.random() * possibleCellStates.size())));
      }
      gridInitialStates.add(rowInitialStates);
    }
    return gridInitialStates;
  }

  /**
   * Edits the existing configuration file given a 2D ArrayList of updated states
   *
   * @param currentStates - 2D ArrayList of updated states
   */
  public void updateStoredConfigFile(List<List<String>> currentStates) {
    // todo: clean up this method
    NodeList gridList = root.getElementsByTagName("gridrow");
    for (int row = 0; row < gridList.getLength(); row++) {
      Element rowElement = (Element) gridList.item(row);
      NodeList rowList = rowElement.getElementsByTagName("gridcell");
      for (int col = 0; col < rowList.getLength(); col++) {
        rowList.item(col).setTextContent(currentStates.get(row).get(col));
      }
    }
    // code from mkyong.com
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = null;
    try {
      transformer = transformerFactory.newTransformer();
    } catch (TransformerConfigurationException e) {
      e.printStackTrace();
    }
    DOMSource source = new DOMSource(xmlDocument);
    StreamResult result = new StreamResult(configFile);
    try {
      transformer.transform(source, result);
    } catch (TransformerException e) {
      e.printStackTrace();
    }
  }

  /**
   * Returns map of cell state names to JavaFX colors that can be interpreted by the View
   * @return - map with cell state names as keys
   * @throws XMLException - if RGB outside 0-255 range is given
   */
  public Map<String, Color> getCellStyles() throws XMLException {
    Element styleElement = getElement(root, "cellstyle");
    NodeList styleList = getNodes(styleElement, "cellstate");
    Map<String, Color> cellStyleMap = new HashMap<>();
    for (int state = 0; state < styleList.getLength(); state++) {
      Element stateElement = (Element) styleList.item(state);
      String cellType = getCurrentAttribute(stateElement,"type");
      try {
        cellStyleMap.put(cellType, new Color(
            Integer.valueOf(stateElement.getElementsByTagName("r").item(0).getTextContent())
                / 255.0,
            Integer.valueOf(stateElement.getElementsByTagName("g").item(0).getTextContent())
                / 255.0,
            Integer.valueOf(stateElement.getElementsByTagName("b").item(0).getTextContent())
                / 255.0,
            1.0));
      }
      catch (IllegalArgumentException e) {
        throw new XMLException(e, "Invalid RGB value given");
      }
    }
    return cellStyleMap;
  }

  private List<String> getCellStateDistribution() {
    Element gridElement = getElement(root, "grid");
    Element distributionElement = getElement(gridElement, "distribution");
    NodeList nodeList = getNodes(distributionElement, "cellstate");
    List<String> distributionList = new ArrayList<>();
    for (int state = 0; state < nodeList.getLength(); state++) {
      Element stateElement = (Element) nodeList.item(state);
      int numDistribution;
      try {
        numDistribution = Integer.valueOf(stateElement.getTextContent());
      } catch (IllegalArgumentException e) {
        throw new XMLException(e);
      }
      if (numDistribution < 0) {
        throw new XMLException(new IllegalArgumentException(),
            "Distribution number cannot be negative");
      }
      for (int i = 0; i < numDistribution; i++) {
        distributionList.add(getCurrentAttribute(stateElement, "type"));
      }
    }
    return distributionList;
  }

  /**
   * Returns map of simulation-specific parameter names and values. Intentionally does
   * NOT throw exceptions for invalid parameter values because the parser does not know
   * about specific simulation needs. Individual rule classes should be able to handle
   * invalid parameter values by offering default values.
   * @return - map with parameter names as keys. 
   */
  public Map<String, String> getParameters() {
    Element configElement = getElement(root, "config_parameters");
    Map<String, String> parameterMap = new HashMap<>();
    if (configElement.hasChildNodes()) {
      NodeList parameterList = getNodes(configElement, "parameter");
      for (int count = 0; count < parameterList.getLength(); count++) {
        Element parameterElement = (Element) parameterList.item(count);
        parameterMap
            .put(getCurrentAttribute(parameterElement, "name"),
                getCurrentAttribute(parameterElement, "value"));
      }
    }
    return parameterMap;
  }

  private List<String> getCellStates() {
    return new ArrayList<>(getCellStyles().keySet());
  }

}
