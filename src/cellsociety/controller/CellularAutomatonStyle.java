package cellsociety.controller;

import cellsociety.xml.XMLStyleParser;
import java.io.File;
import java.util.Collections;
import java.util.Map;
import javafx.scene.paint.Color;

/**
 * This class stores all information read in from the style file, to be
 * passed to the View when the simulation is initialized
 */
public class CellularAutomatonStyle {
  private String language;
  private int cellWidth;
  private int cellHeight;
  private boolean cellOutlines;
  private String cellShape;
  private Map<String, Color> altColorMap;

  /**
   * Parses through XML style file
   * This class does not throw errors for poorly formatted or missing XML elements,
   * since all styling is optional. Hence, it is the responsibility of the View to check
   * that parsed values are valid.
   * @param styleFile - XML style file
   */
  public CellularAutomatonStyle(File styleFile) {
    XMLStyleParser styleParser = new XMLStyleParser(styleFile);
    parseStyleFile(styleParser);
  }

  private void parseStyleFile(XMLStyleParser styleParser) {
    language = styleParser.getLanguage();
    cellWidth = styleParser.getCellWidth();
    cellHeight = styleParser.getCellHeight();
    cellOutlines = styleParser.getCellOutlines();
    cellShape = styleParser.getCellShape();
    altColorMap = styleParser.getAltColors();
  }

  /**
   * Returns desired language, or empty String if not specified
   * Specified language may not be supported. The View must check for validity
   * @return - name of language or empty string
   */
  public String getLanguage() {
    return language;
  }

  /**
   * Returns desired cell width, or 0 if not specified
   * If specified, cell width is guaranteed to be positive
   * @return - cell width in pixels, or 0
   */
  public int getCellWidth() {
    return cellWidth;
  }

  /**
   * Returns desired cell height, or 0 if not specified
   * If specified, cell height is guaranteed to be positive
   * @return - cell height in pixels, or 0
   */
  public int getCellHeight() {
    return cellHeight;
  }

  /**
   * Returns whether the cells should be outlined. Only returns false if the user
   * specifically set the outline attribute to "n" or "N." Otherwise, the style
   * defaults to turning outlines on.
   * @return - true if cells should be outlined, false if they should not
   */
  public boolean getCellOutlines() {
    return cellOutlines;
  }

  /**
   * Returns desired cell shape, or empty string if not specified
   * Specified cell shape may not be supported. The View must check for validity
   * @return - name of cell shape or empty string
   */
  public String getCellShape() {
    return cellShape;
  }

  /**
   * Returns read-only copy of map of alternative colors for various states
   * May be empty, and not all states may be represented. It is the View's responsibility
   * to parse through the map to change the color of a specific state
   * @return - map where the keys are Strings that contain one or more space-delimited state names
   * and the values are the Colors that should be associated with any of the corresponding states
   */
  public Map<String, Color> getAltColorMap() {
    return Collections.unmodifiableMap(altColorMap);
  }
}
