package cellsociety.xml;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.paint.Color;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class XMLStyleParser extends XMLGenericParser {

  /**
   * Create parser for any XML file input
   *
   * @param file - XML configuration file
   * @throws XMLException
   */
  public XMLStyleParser(File file) throws XMLException {
    super(file);
  }

  /**
   * Returns desired language, if specified
   *
   * @return
   */
  public String getLanguage() {
    return getTextValue(root, "language");
  }

  /**
   * Returns desired cell width in pixels, if specified
   *
   * @return
   */
  public int getCellWidth() {
    return Integer.valueOf(getAttribute("cell", "width"));
  }

  /**
   * Returns desired cell height in pixels, if specified
   *
   * @return
   */
  public int getCellHeight() {
    return Integer.valueOf(getAttribute("cell", "height"));
  }

  /**
   * Returns whether the cells should be outlined, if specified
   *
   * @return
   */
  public boolean getCellOutlines() {
    return getAttribute("cell", "outline").toUpperCase().equals("Y");
  }

  /**
   * Returns desired cell shape, either a geometric shape or image, if specified
   *
   * @return
   */
  public String getCellShape() {
    return getAttribute("cell", "shape");
  }

  /**
   * Returns map of alternative colors, with all possible state names space-delimited as the keys
   *
   * @return
   */
  public Map<String, Color> getAltColors() {
    Map<String, Color> altColors = new HashMap<>();
    NodeList colorNodes = root.getElementsByTagName("altcolor");
    if (colorNodes != null) {
      for (int i = 0; i < colorNodes.getLength(); i++) {
        Element altColor = (Element) colorNodes.item(i);
        altColors
            .put(altColor.getTextContent(), new Color(Integer.valueOf(altColor.getAttribute("r")),
                Integer.valueOf(altColor.getAttribute("g")),
                Integer.valueOf(altColor.getAttribute("b")),
                1.0));
      }
    }
    return altColors;
  }
}
