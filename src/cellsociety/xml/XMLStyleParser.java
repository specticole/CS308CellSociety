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
   * @throws XMLException - if something goes wrong trying to set up an XML parser
   */
  public XMLStyleParser(File file) throws XMLException {
    super(file);
  }

  /**
   * Returns desired language, if specified
   *
   * @return - String representation of desired language if specified, or empty string
   */
  public String getLanguage() {
    if (root.getElementsByTagName("language") != null
        && root.getElementsByTagName("language").getLength() > 0) {
      return getTextValue(root, "language");
    } else {
      return "";
    }
  }

  /**
   * Returns desired cell width in pixels, if specified
   *
   * @return - desired cell width in pixels, or 0 if value is not given or invalid
   */
  public int getCellWidth() {
    if (root.getElementsByTagName("cell") != null
        && root.getElementsByTagName("cell").getLength() > 0) {
      Element cellElement = (Element) root.getElementsByTagName("cell").item(0);
      if (cellElement.hasAttribute("width")) {
        int cellWidth = Integer.valueOf(getAttribute(root,"cell", "width"));
        if (cellWidth > 0) {
          return cellWidth;
        }
      }
    }
    return 0;
  }

  /**
   * Returns desired cell height in pixels, if specified
   *
   * @return - desired cell height in pixels, or 0 if value is not given or invalid
   */
  public int getCellHeight() {
    if (root.getElementsByTagName("cell") != null
        && root.getElementsByTagName("cell").getLength() > 0) {
      Element cellElement = (Element) root.getElementsByTagName("cell").item(0);
      if (cellElement.hasAttribute("height")) {
        int cellHeight = Integer.valueOf(getAttribute(root,"cell", "height"));
        if (cellHeight > 0) {
          return cellHeight;
        }
      }
    }
    return 0;
  }

  /**
   * Returns whether the cells should be outlined, if specified
   *
   * @return - true if the outline attribute is "y" or "Y," false otherwise
   */
  public boolean getCellOutlines() {
    if (root.getElementsByTagName("cell") != null
        && root.getElementsByTagName("cell").getLength() > 0) {
      Element cellElement = (Element) root.getElementsByTagName("cell").item(0);
      if (cellElement.hasAttribute("outline")) {
        return getAttribute(root,"cell", "outline").toUpperCase().equals("Y");
      }
    }
    return false;
  }

  /**
   * Returns desired cell shape, either a geometric shape or image, if specified
   *
   * @return - desired cell shape if specified, empty string otherwise
   */
  public String getCellShape() {
    if (root.getElementsByTagName("cell") != null
        && root.getElementsByTagName("cell").getLength() > 0) {
      Element cellElement = (Element) root.getElementsByTagName("cell").item(0);
      if (cellElement.hasAttribute("shape")) {
        return getAttribute(root,"cell", "shape");
      }
    }
    return "";
  }

  /**
   * Returns map of alternative colors, with all possible state names space-delimited as the keys
   *
   * @return - map of alternative colors, if specified
   */
  public Map<String, Color> getAltColors() throws XMLException {
    Map<String, Color> altColors = new HashMap<>();
    NodeList colorNodes = root.getElementsByTagName("altcolor");
    if (colorNodes != null && colorNodes.getLength() > 0) {
      for (int i = 0; i < colorNodes.getLength(); i++) {
        Element altColor = (Element) colorNodes.item(i);
        try {
          altColors
              .put(altColor.getTextContent(), new Color(Integer.valueOf(altColor.getAttribute("r")),
                  Integer.valueOf(altColor.getAttribute("g")),
                  Integer.valueOf(altColor.getAttribute("b")),
                  1.0));
        } catch (IllegalArgumentException e) {
          throw new XMLException(e, "Alternative color invalid");
        }
      }
    }
    return altColors;
  }
}
