package com.solution.gedcom.core;

import com.solution.gedcom.groups.UnitTests;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import static org.junit.Assert.assertEquals;


@Category(UnitTests.class)
public class XMLToStringConverterTest extends BaseXMLTest {

    private XMLToStringConverter xmlToStringConverter;

    @Before
    public void setup() {
        super.setup();
        xmlToStringConverter = new XMLToStringConverter(configuration);
    }

    @Test
    public void testGenerateXMLString() throws ParserConfigurationException, TransformerException {
        String xmlString = xmlToStringConverter.generateXMLString(baseDocument());
        assertEquals(gedcomOutputWithoutData(), xmlString);
    }

    @Test
    public void testGenerateXMLStringForNullDocument() throws ParserConfigurationException, TransformerException {
        String xmlString = xmlToStringConverter.generateXMLString(null);
        assertEquals(nullDocumentOutput(), xmlString);
    }

    @Test
    public void testGenerateFormattedOutputWhenIndentationIsFalse() throws ParserConfigurationException, TransformerException {
        outputFileConfig.getXmlConfig().setIndent(false);
        String xmlString = xmlToStringConverter.generateXMLString(inputDocument());
        assertEquals(outputWithoutIndentation(), xmlString);
    }

    private Document baseDocument() throws ParserConfigurationException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        Element root = doc.createElement("gedcom");
        doc.appendChild(root);
        return doc;
    }

    private Document inputDocument() throws ParserConfigurationException {
        Document document = baseDocument();
        Element indi = document.createElement("indi");
        indi.setAttribute("id", "@I0001@");
        Element name = document.createElement("name");
        name.appendChild(document.createTextNode("Elizabeth"));
        indi.appendChild(name);
        document.getDocumentElement().appendChild(indi);
        return document;
    }

    private String nullDocumentOutput() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n\n";
    }

    private String outputWithoutIndentation() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><gedcom><indi id=\"@I0001@\"><name>Elizabeth</name></indi></gedcom>";
    }
}
