package cellsociety;

import java.io.IOException;
import java.util.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import org.xml.sax.SAXException;

public class CellularAutomatonController {
  private String configFileName;

  // Outputs of loadConfigFile method
  private HashMap<String, String> metaMap; // keys: title, author, description
  private String gridType;
  private int gridWidth;
  private int gridHeight;
  private List<List<String>> gridInitialStates; // inner lists are rows of the grid
  private HashMap<String, ArrayList<Integer>> cellStyleMap; // keys are String representations of cell states, values are ArrayLists of RGB values
  private HashMap<String, String> parameterMap; // keys are parameter names, values are parameter values

  public CellularAutomatonController(String configFileName) {
    this.configFileName = configFileName;
  }

  // uses mkyong.com as reference
  public void loadConfigFile() {
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document parserDoc = builder.parse(this.getClass().getClassLoader().getResourceAsStream(configFileName));

      parserDoc.getDocumentElement().normalize();
      //System.out.println("Root element: " + parserDoc.getDocumentElement().getNodeName());
      String simulationRules = parserDoc.getDocumentElement().getAttribute("rules");
      System.out.println(simulationRules);

      metaMap = new HashMap<>();
      Element metaNode = (Element) parserDoc.getElementsByTagName("meta").item(0);
      String simulationTitle = metaNode.getElementsByTagName("title").item(0).getTextContent();
      metaMap.put("title", simulationTitle);
      String simulationAuthor = metaNode.getElementsByTagName("author").item(0).getTextContent();
      metaMap.put("author", simulationAuthor);
      String simulationDescription = metaNode.getElementsByTagName("description").item(0).getTextContent();
      metaMap.put("description", simulationDescription);
      System.out.println(metaMap);

      Element gridElement = (Element) parserDoc.getElementsByTagName("grid").item(0);
      gridType = gridElement.getAttribute("type");
      System.out.println("Grid type: " + gridType);
      gridWidth = Integer.valueOf(gridElement.getAttribute("width"));
      System.out.println("Grid width: " + gridWidth);
      gridHeight = Integer.valueOf(gridElement.getAttribute("height"));
      System.out.println("Grid height: " + gridHeight);

      gridInitialStates = new ArrayList<>();
      NodeList gridList = gridElement.getElementsByTagName("gridrow");
      for (int row = 0; row < gridList.getLength(); row++) {
        Element rowElement = (Element) gridList.item(row);
        NodeList rowList = rowElement.getElementsByTagName("gridcell");
        ArrayList<String> rowInitialStates = new ArrayList<>();
        for (int col = 0; col < rowList.getLength(); col++) {
          rowInitialStates.add(rowList.item(col).getTextContent());
        }
        gridInitialStates.add(rowInitialStates);
      }
      System.out.println(gridInitialStates);

      Element styleElement = (Element) parserDoc.getElementsByTagName("cellstyle").item(0);
      NodeList styleList = styleElement.getElementsByTagName("cellstate");
      cellStyleMap = new HashMap<>();
      for (int state = 0; state < styleList.getLength(); state++) {
        Element stateElement = (Element) styleList.item(state);
        String cellType = stateElement.getAttribute("type");
        ArrayList<Integer> styleRGBValues = new ArrayList<>();
        styleRGBValues.add(Integer.valueOf(stateElement.getElementsByTagName("r").item(0).getTextContent()));
        styleRGBValues.add(Integer.valueOf(stateElement.getElementsByTagName("g").item(0).getTextContent()));
        styleRGBValues.add(Integer.valueOf(stateElement.getElementsByTagName("b").item(0).getTextContent()));
        cellStyleMap.put(cellType, styleRGBValues);
      }
      System.out.println(cellStyleMap);

      Element configElement = (Element) parserDoc.getElementsByTagName("config_parameters").item(0);
      parameterMap = new HashMap<>();
      if (configElement.hasChildNodes()) {
        NodeList parameterList = configElement.getElementsByTagName("parameter");
        for (int count = 0; count < parameterList.getLength(); count++) {
          Element parameterElement = (Element) parameterList.item(count);
          parameterMap.put(parameterElement.getAttribute("name"), parameterElement.getAttribute("value"));
        }
      }
      System.out.println(parameterMap);

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public void playSimulation() {

  }

  public void pauseSimulation() {

  }

  public void changeAnimationRate(double rate) {

  }

  public void step() {

  }

  public void updateViews(int newTime) {
    
  }

}
