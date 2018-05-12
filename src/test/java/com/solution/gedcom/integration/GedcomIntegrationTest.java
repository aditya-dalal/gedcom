package com.solution.gedcom.integration;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.solution.gedcom.GedcomModule;
import com.solution.gedcom.core.*;
import com.solution.gedcom.groups.IntegrationTests;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@Category(IntegrationTests.class)
public class GedcomIntegrationTest {

    @Test
    public void testGedcomToXMLGeneration() throws IOException {
        String configFile = getClass().getClassLoader().getResource("testconfig.yml").getFile();
        String inputFile = getClass().getClassLoader().getResource("sample.txt").getFile();
        String outputFile = "/tmp/" + System.currentTimeMillis() + ".xml";

        Injector injector = Guice.createInjector(new GedcomModule(configFile, inputFile, outputFile));
        OutputFormat xmlFileFormat = injector.getInstance(XMLFormat.class);
        InputSource input = injector.getInstance(InputFromFile.class);
        OutputDestination output = injector.getInstance(OutputToFile.class);

        String xmlString = xmlFileFormat.generateFormattedOutput(input.readFromSource());
        assertEquals(expectedXML(), xmlString);
        output.writeToDestination(xmlString);
        File file = new File(outputFile);
        assertTrue(file.exists() && !file.isDirectory());
        file.delete();
    }

    private String expectedXML() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                "<gedcom>\n" +
                "   <indi id=\"@I0001@\">\n" +
                "      <name>Elizabeth Alexandra Mary /Windsor/</name>\n" +
                "      <sex>F</sex>\n" +
                "      <birt>\n" +
                "         <date>21 Apr 1926</date>\n" +
                "         <plac>17 Bruton Street, London, W1</plac>\n" +
                "      </birt>\n" +
                "      <occu>Queen</occu>\n" +
                "      <famc id=\"@F0003@\"/>\n" +
                "      <fams id=\"@F0001@\"/>\n" +
                "      <note id=\"@N0002@\"/>\n" +
                "      <chan>\n" +
                "         <date>\n" +
                "            <day>13</day>\n" +
                "            <month>Dec</month>\n" +
                "            <year>2003</year>\n" +
                "         </date>\n" +
                "      </chan>\n" +
                "   </indi>\n" +
                "   <indi id=\"@I0002@\">\n" +
                "      <name>Philip /Mountbatten/</name>\n" +
                "      <sex>M</sex>\n" +
                "      <birt>\n" +
                "         <date>1921</date>\n" +
                "      </birt>\n" +
                "      <titl>Duke of Edinburgh</titl>\n" +
                "      <famc id=\"@F0002@\"/>\n" +
                "      <fams id=\"@F0001@\"/>\n" +
                "      <note id=\"@N0001@\"/>\n" +
                "      <chan>\n" +
                "         <date>6 Mar 2004</date>\n" +
                "      </chan>\n" +
                "   </indi>\n" +
                "   <indi id=\"@I0003@\">\n" +
                "      <name>Andrew</name>\n" +
                "      <sex>M</sex>\n" +
                "      <fams id=\"@F0002@\"/>\n" +
                "      <note id=\"@N0013@\"/>\n" +
                "      <chan>\n" +
                "         <date>6 Mar 2004</date>\n" +
                "      </chan>\n" +
                "   </indi>\n" +
                "   <trlr/>\n" +
                "</gedcom>\n";
    }
}
