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
