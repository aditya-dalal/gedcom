package com.solution.gedcom.core;

import com.google.inject.Inject;
import com.solution.gedcom.config.Configuration;
import com.solution.gedcom.config.OutputFileConfig;
import org.w3c.dom.Document;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;


public class XMLToStringConverter {

    private OutputFileConfig.XMLConfig xmlConfig;
    private static final String TRANSFORMER_INDENT_PROPERTY = "{http://xml.apache.org/xslt}indent-amount";

    @Inject
    public XMLToStringConverter(Configuration configuration) {
        xmlConfig = configuration.getOutputFileConfig().getXmlConfig();
    }

    public String generateXMLString(Document doc) throws TransformerException {
        Transformer transformer = getTransformer();
        DOMSource source = new DOMSource(doc);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        transformer.transform(source, result);
        return writer.toString();
    }

    private Transformer getTransformer() throws TransformerConfigurationException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        if(xmlConfig.isIndent()) {
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(TRANSFORMER_INDENT_PROPERTY, String.valueOf(xmlConfig.getIndentValue()));
        }
        return transformer;
    }
}
