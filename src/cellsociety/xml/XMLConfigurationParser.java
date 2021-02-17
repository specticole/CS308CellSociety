package cellsociety.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javafx.scene.paint.Color;
import java.util.HashMap;
import java.util.Map;
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

  public static final String[] META_FIELDS = {"title", "author", "description"};

  /**
   * Create parser for any XML file input
   *
   * @param file - XML configuration file
   * @throws XMLException
   */
  public XMLConfigurationParser(File file) throws XMLException {
    super(file);
  }

  public Map<String, String> getMetadata() {
    Map<String, String> simulationMetadata = new HashMap<>();
    for (String field : META_FIELDS) {
      simulationMetadata.put(field, getTextValue(root, field));
    }
    return simulationMetadata;
  }

  public String getSimulationType() {
    return root.getAttribute("rules");
  }

  public String getGridType() {
    return getAttribute("grid", "type");
  }

  public int getGridWidth() {
    return Integer.valueOf(getAttribute("grid", "width"));
  }

  public int getGridHeight() {
    return Integer.valueOf(getAttribute("grid", "height"));
  }

  public int getGridNeighbors() {
    return Integer.valueOf(getAttribute("grid", "neighbors"));
  }

  public boolean getGridWrapping() {
    return Boolean.valueOf(getAttribute("grid", "wrapping"));
  }

  public List<List<String>> getInitialStates() {
    Element gridElement = (Element) root.getElementsByTagName("grid").item(0);
    if (gridElement.hasAttribute("distribution")) {
      switch (gridElement.getAttribute("distribution").toLowerCase()) {
        case "specified":
          return getSpecifiedInitialStates();
        case "randomspecified":
          break; // todo: randomly pick state at specified locations, with or without desired distribution
        case "randomtotal":
          break; // todo: randomly pick state based on total locations to occupy, with or without desired distribution
      }
    }
    return getSpecifiedInitialStates();
  }

  private List<List<String>> getSpecifiedInitialStates() {
    List<List<String>> gridInitialStates = new ArrayList<>();
    NodeList gridList = root.getElementsByTagName("gridrow");
    for (int row = 0; row < gridList.getLength(); row++) {
      Element rowElement = (Element) gridList.item(row);
      NodeList rowList = rowElement.getElementsByTagName("gridcell");
      ArrayList<String> rowInitialStates = new ArrayList<>();
      for (int col = 0; col < rowList.getLength(); col++) {
        rowInitialStates.add(rowList.item(col).getTextContent());
      }
      gridInitialStates.add(rowInitialStates);
    }
    return gridInitialStates;
  }

  /**
   * Edits the existing configuration file given a 2D ArrayList of updated states
   * @param currentStates - 2D ArrayList of updated states
   */
  public void updateStoredConfigFile(List<List<String>> currentStates) {
    // todo: clean up this method
    System.out.println("updating...");
    NodeList gridList = root.getElementsByTagName("gridrow");
    for (int row = 0; row < gridList.getLength(); row++) {
      Element rowElement = (Element) gridList.item(row);
      NodeList rowList = rowElement.getElementsByTagName("gridcell");
      for (int col = 0; col < rowList.getLength(); col++) {
        System.out.println("Changing state to " + currentStates.get(row).get(col));
        rowList.item(col).setTextContent(currentStates.get(row).get(col));
      }
    }
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

  public Map<String, Color> getCellStyles() {
    Element styleElement = (Element) root.getElementsByTagName("cellstyle").item(0);
    NodeList styleList = styleElement.getElementsByTagName("cellstate");
    Map<String, Color> cellStyleMap = new HashMap<>();
    for (int state = 0; state < styleList.getLength(); state++) {
      Element stateElement = (Element) styleList.item(state);
      String cellType = stateElement.getAttribute("type");
      cellStyleMap.put(cellType, new Color(
          Integer.valueOf(stateElement.getElementsByTagName("r").item(0).getTextContent()) / 255.0,
          Integer.valueOf(stateElement.getElementsByTagName("g").item(0).getTextContent()) / 255.0,
          Integer.valueOf(stateElement.getElementsByTagName("b").item(0).getTextContent()) / 255.0,
          1.0));
    }
    return cellStyleMap;
  }

  public Map<String, String> getParameters() {
    Element configElement = (Element) root.getElementsByTagName("config_parameters").item(0);
    Map<String, String> parameterMap = new HashMap<>();
    if (configElement.hasChildNodes()) {
      NodeList parameterList = configElement.getElementsByTagName("parameter");
      for (int count = 0; count < parameterList.getLength(); count++) {
        Element parameterElement = (Element) parameterList.item(count);
        parameterMap
            .put(parameterElement.getAttribute("name"), parameterElement.getAttribute("value"));
      }
    }
    return parameterMap;
  }

  private Set<String> getCellStates() {
    return getCellStyles().keySet();
  }

}
