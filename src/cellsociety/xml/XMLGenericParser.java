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

/**
 * This class creates an XML document parser and provides several generic methods that can
 * obtain attributes or values or throw an error if the desired property is non-existent
 *
 * @author Patrick Liu
 */
public class XMLGenericParser {

  // keep only one documentBuilder because it is expensive to make and can reset it before parsing
  protected final DocumentBuilder DOCUMENT_BUILDER;
  protected Element root;
  protected Document xmlDocument;
  protected File configFile;

  /**
   * Create parser for any XML file input
   *
   * @param file - XML configuration file
   * @throws XMLException - if an XML parser is unable to be created
   */
  public XMLGenericParser(File file) throws XMLException {
    configFile = file;
    DOCUMENT_BUILDER = getDocumentBuilder();
    root = getRootElement(file);
  }

  /**
   * Returns the root element of the given XML file
   *
   * @param xmlFile - the input XML file
   * @return - an Element representing the root node
   * @throws XMLException - if the file is empty or is unable to be read
   */
  protected Element getRootElement(File xmlFile) throws XMLException {
    try {
      DOCUMENT_BUILDER.reset();
      xmlDocument = DOCUMENT_BUILDER.parse(xmlFile);
      return xmlDocument.getDocumentElement();
    } catch (SAXException | IOException e) {
      throw new XMLException(e);
    }
  }

  /**
   * Returns the first element in the parent element's child nodes whose tag
   * matches the input String
   *
   * @param parent - Element whose children nodes should be searched
   * @param elementName - desired tag name
   * @return - the first child Element that matches the desired tag name
   * @throws XMLException - if no node matches the desired tag name
   */
  protected Element getElement(Element parent, String elementName) throws XMLException {
    NodeList nodeList = parent.getElementsByTagName(elementName);
    if (nodeList != null && nodeList.getLength() > 0) {
      return (Element) parent.getElementsByTagName(elementName).item(0);
    }
    else {
      throw new XMLException(new IllegalArgumentException(), "Missing " + elementName + " tag in XML file");
    }
  }

  /**
   * Returns all children nodes whose tags match the input String
   *
   * @param parent - Element whose children nodes should be searched
   * @param tagName - desired tag name
   * @return - a list of all children nodes that match the desired tag name
   * @throws XMLException - if no node matches the desired tag name
   */
  protected NodeList getNodes(Element parent, String tagName) throws XMLException {
    NodeList nodes = parent.getElementsByTagName(tagName);
    if (nodes != null && nodes.getLength() > 0) {
      return nodes;
    }
    else {
      throw new XMLException(new IllegalArgumentException());
    }
  }

  /**
   * Returns the value of a given attribute
   *
   * @param parent - Element whose children nodes should be searched for instances of the tag name
   * @param tagName - desired tag name
   * @param attributeName - desired attribute name
   * @return - the value of the desired attribute
   * @throws XMLException - if the node or attribute does not exist
   */
  protected String getAttribute(Element parent, String tagName, String attributeName) throws XMLException {
    try {
      return getElement(parent, tagName).getAttribute(attributeName);
    }
    catch (IllegalArgumentException e) {
      throw new XMLException(e, "Missing " + attributeName + " attribute");
    }
  }

  /**
   * Returns the value of an attribute in one specific Element
   *
   * @param element - the Element that should contain the desired attribute
   * @param attributeName - desired attribute name
   * @return - the value of the desired attribute
   * @throws XMLException - if the attribute does not exist
   */
  protected String getCurrentAttribute(Element element, String attributeName) throws XMLException {
    try {
      return element.getAttribute(attributeName);
    }
    catch (IllegalArgumentException e) {
      throw new XMLException(e, "Missing " + attributeName + " attribute");
    }
  }

  /**
   * Returns value of an Element's text
   *
   * @param e - the Element whose children nodes should be search
   * @param tagName - desired tag name
   * @return - the value of the found Element's text
   * @throws XMLException - if no matching children nodes are found or the text value is empty
   */
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
