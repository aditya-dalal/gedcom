package com.solution.gedcom.core;

import com.google.inject.Inject;
import com.solution.gedcom.config.Configuration;
import com.solution.gedcom.config.InputFileConfig;
import com.solution.gedcom.config.OutputFileConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import java.io.*;
import java.util.LinkedList;
import java.util.List;


public class XMLFormat implements OutputFormat {

    private InputFileConfig inputConfig;
    private OutputFileConfig.XMLConfig xmlConfig;
    private XMLToStringConverter xmlToStringConverter;
    private List<Element> elementPath;
    private static final Logger LOGGER = LoggerFactory.getLogger(XMLFormat.class);

    @Inject
    public XMLFormat(Configuration configuration, XMLToStringConverter xmlToStringConverter) {
        inputConfig = configuration.getInputFileConfig();
        xmlConfig = configuration.getOutputFileConfig().getXmlConfig();
        this.xmlToStringConverter = xmlToStringConverter;
        elementPath = new LinkedList<>();
    }

    public String generateFormattedOutput(BufferedReader bufferedReader) throws IOException {
        if(bufferedReader == null)
            throw new IOException("Unable to read from input buffer.");
        String xmlString = null;
        try {
            Document doc = getDocument();
            setRootElement(doc);
            addElementsToDocument(bufferedReader, doc);
            xmlString = xmlToStringConverter.generateXMLString(doc);
        } catch (ParserConfigurationException | TransformerException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return xmlString;
    }

    private void addElementsToDocument(BufferedReader reader, Document doc) throws IOException {
        String inputLine;
        while ((inputLine = reader.readLine()) != null) {
            if(isLineEmpty(inputLine))
                continue;
            processInputLine(doc, inputLine);
        }
        reader.close();
    }

    private void setRootElement(Document doc) {
        Element rootElement = getElement(doc, xmlConfig.getDocumentRoot());
        doc.appendChild(rootElement);
        elementPath.add(rootElement);
    }

    private Document getDocument() throws ParserConfigurationException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        return docBuilder.newDocument();
    }

    private void processInputLine(Document doc, String inputLine) {
        String[] fields = inputLine.split(inputConfig.getFieldSeparator());
        int requiredElementLevel = getRequiredElementLevel(fields[0]);
        if(requiredElementLevel < 0 || fields.length < 2)
            return;
        Element element;
        if(hasId(inputLine)) {
            if(fields.length < 3)
                return;
            else {
                element = getElement(doc, getElementTag(fields[1], fields[2]));
                element.setAttribute(xmlConfig.getAttributeName(), getAttributeValue(fields[1], fields[2]));
            }
        }
        else {
            element = getElement(doc, fields[1]);
            if(fields.length > 2)
                element.appendChild(getTextNode(doc, inputLine, fields[2]));
        }
        addElementAtRequiredLevel(element, requiredElementLevel);
    }

    private int getRequiredElementLevel(String levelString) {
        try {
            return Integer.parseInt(levelString);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void addElementAtRequiredLevel(Element element, int requiredElementLevel) {
        int currentElementLevel = elementPath.size() - 1;
        if(requiredElementLevel < currentElementLevel) {
            while (currentElementLevel != requiredElementLevel)
                elementPath.remove(currentElementLevel--);
        }
        elementPath.get(currentElementLevel).appendChild(element);
        elementPath.add(element);
    }

    private Text getTextNode(Document doc, String inputLine, String nodeTextStart) {
        String nodeText = inputLine.substring(inputLine.indexOf(nodeTextStart));
        return doc.createTextNode(nodeText);
    }

    private String getAttributeValue(String field1, String field2) {
        return field1.startsWith(inputConfig.getAttributeIdentifier()) ? field1 : field2;
    }

    private String getElementTag(String field1, String field2) {
        return field1.startsWith(inputConfig.getAttributeIdentifier()) ? field2 : field1;
    }

    private boolean hasId(String line) {
        return line.contains(inputConfig.getAttributeIdentifier());
    }

    private boolean isLineEmpty(String inputLine) {
        return inputLine.isEmpty() || inputLine.trim().length() < 1;
    }

    private Element getElement(Document doc, String field) {
        return doc.createElement(field.toLowerCase());
    }
}
