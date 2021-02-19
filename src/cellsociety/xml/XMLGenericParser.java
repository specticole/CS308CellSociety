package cellsociety.xml;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLGenericParser {

  // Readable error message that can be displayed by the GUI
  public static final String ERROR_MESSAGE = "XML file does not represent %s";
  // keep only one documentBuilder because it is expensive to make and can reset it before parsing
  protected final DocumentBuilder DOCUMENT_BUILDER;
  protected Element root;
  protected Document xmlDocument;
  protected File configFile;

  /**
   * Create parser for any XML file input
   *
   * @param file - XML configuration file
   * @throws XMLException
   */
  public XMLGenericParser(File file) throws XMLException {
    configFile = file;
    DOCUMENT_BUILDER = getDocumentBuilder();
    root = getRootElement(file);
  }

  protected Element getRootElement(File xmlFile) throws XMLException {
    try {
      DOCUMENT_BUILDER.reset();
      xmlDocument = DOCUMENT_BUILDER.parse(xmlFile);
      return xmlDocument.getDocumentElement();
    } catch (SAXException | IOException e) {
      throw new XMLException(e);
    }
  }

  protected Element getElement(Element parent, String elementName) throws XMLException {
    NodeList nodeList = parent.getElementsByTagName(elementName);
    if (nodeList != null && nodeList.getLength() > 0) {
      return (Element) parent.getElementsByTagName(elementName).item(0);
    }
    else {
      throw new XMLException(new IllegalArgumentException(), "Missing " + elementName + " tag in XML file");
    }
  }

  // get value of Element's attribute
  protected String getAttribute(String tagName, String attributeName) {
    NodeList nodeList = root.getElementsByTagName(tagName);
    if (nodeList != null && nodeList.getLength() > 0) {
      Element e = (Element) nodeList.item(0);
      return e.getAttribute(attributeName);
    } else {
      return "";
    }
  }

  protected String getAttribute(Element parent, String tagName, String attributeName) throws XMLException {
    try {
      return getElement(parent, tagName).getAttribute(attributeName);
    }
    catch (IllegalArgumentException e) {
      throw new XMLException(e, "Missing " + attributeName + " attribute");
    }
  }

  // get value of Element's text
  protected String getTextValue(Element e, String tagName) throws XMLException {
    NodeList nodeList = e.getElementsByTagName(tagName);
    if (nodeList != null && nodeList.getLength() > 0) {
      return nodeList.item(0).getTextContent();
    } else {
      throw new XMLException(new IllegalArgumentException(), tagName + " tag missing text value");
    }
  }

  // boilerplate code needed to make a documentBuilder
  protected DocumentBuilder getDocumentBuilder() throws XMLException {
    try {
      return DocumentBuilderFactory.newInstance().newDocumentBuilder();
    } catch (ParserConfigurationException e) {
      throw new XMLException(e);
    }
  }
}
