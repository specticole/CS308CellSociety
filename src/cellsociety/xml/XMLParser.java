package cellsociety.xml;

import java.awt.Color;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * This class handles parsing XML files and returning appropriately-formatted
 * Objects for given input tags. Heavily based on code from class.
 *
 * @author Rhondu Smithwick
 * @author Robert C. Duvall
 * @author Patrick Liu
 */
public class XMLParser {
  // Readable error message that can be displayed by the GUI
  public static final String ERROR_MESSAGE = "XML file does not represent %s";
  public static final String[] META_FIELDS = {"title", "author", "description"};
  // keep only one documentBuilder because it is expensive to make and can reset it before parsing
  private final DocumentBuilder DOCUMENT_BUILDER;
  private Element root;


  /**
   * Create parser for XML files of given type.
   */
  public XMLParser (String fileName) throws XMLException {
    DOCUMENT_BUILDER = getDocumentBuilder();
    root = getRootElement(this.getClass().getClassLoader().getResourceAsStream(fileName));
  }

  public Map<String, String> getMetadata () {
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

  public List<List<String>> getInitialStates() {
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

  public Map<String, Color> getCellStyles() {
    Element styleElement = (Element) root.getElementsByTagName("cellstyle").item(0);
    NodeList styleList = styleElement.getElementsByTagName("cellstate");
    Map<String, Color> cellStyleMap = new HashMap<>();
    for (int state = 0; state < styleList.getLength(); state++) {
      Element stateElement = (Element) styleList.item(state);
      String cellType = stateElement.getAttribute("type");
      cellStyleMap.put(cellType, new Color(Integer.valueOf(stateElement.getElementsByTagName("r").item(0).getTextContent()),
          Integer.valueOf(stateElement.getElementsByTagName("g").item(0).getTextContent()),
          Integer.valueOf(stateElement.getElementsByTagName("b").item(0).getTextContent())));
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
        parameterMap.put(parameterElement.getAttribute("name"), parameterElement.getAttribute("value"));
      }
    }
    return parameterMap;
  }

  // get root element of an XML file
  private Element getRootElement (InputStream xmlFile) throws XMLException {
    try {
      DOCUMENT_BUILDER.reset();
      Document xmlDocument = DOCUMENT_BUILDER.parse(xmlFile);
      return xmlDocument.getDocumentElement();
    }
    catch (SAXException | IOException e) {
      throw new XMLException(e);
    }
  }

  // get value of Element's attribute
  private String getAttribute (String tagName, String attributeName) {
    NodeList nodeList = root.getElementsByTagName(tagName);
    if (nodeList != null && nodeList.getLength() > 0) {
      Element e = (Element) nodeList.item(0);
      return e.getAttribute(attributeName);
    }
    else {
      return "";
    }
  }

  // get value of Element's text
  private String getTextValue (Element e, String tagName) {
    NodeList nodeList = e.getElementsByTagName(tagName);
    if (nodeList != null && nodeList.getLength() > 0) {
      return nodeList.item(0).getTextContent();
    }
    else {
      // FIXME: empty string or exception? In some cases it may be an error to not find any text
      return "";
    }
  }

  // boilerplate code needed to make a documentBuilder
  private DocumentBuilder getDocumentBuilder () throws XMLException {
    try {
      return DocumentBuilderFactory.newInstance().newDocumentBuilder();
    }
    catch (ParserConfigurationException e) {
      throw new XMLException(e);
    }
  }
}
