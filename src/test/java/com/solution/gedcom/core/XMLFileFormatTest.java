package com.solution.gedcom.core;

import com.solution.gedcom.groups.UnitTests;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;

import javax.xml.transform.TransformerException;
import java.io.BufferedReader;
import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;


@Category(UnitTests.class)
public class XMLFileFormatTest extends BaseXMLTest {

    private OutputFormat xmlFileFormat;

    @Mock
    private BufferedReader reader;
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() {
        super.setup();
        XMLToStringConverter xmlToStringConverter = new XMLToStringConverter(configuration);
        xmlFileFormat = new XMLFormat(configuration, xmlToStringConverter);
    }

    @Test
    public void testGenerateFormattedOutputWithoutInputData() throws IOException, TransformerException {
        String xmlString = xmlFileFormat.generateFormattedOutput(reader);
        assertEquals(gedcomOutputWithoutData(), xmlString);
    }

    @Test
    public void testGenerateFormattedOutputWithInputData() throws IOException {
        when(reader.readLine()).thenReturn("0 @I0001@ INDI", "1 NAME Elizabeth Alexandra Mary /Windsor/",
                "1 BIRT", "2 DATE 21 Apr 1926", "1 FAMC @F0003@", "1 CHAN", "2 DATE", "3 Month Dec", "0 @I0002@ INDI",
                "1 NAME Philip /Mountbatten/", "0 TRLR", null);
        String xmlString = xmlFileFormat.generateFormattedOutput(reader);
        assertEquals(outputForValidData(), xmlString);
    }

    @Test
    public void testGenerateFormattedOutputWithEmptyLines() throws IOException {
        when(reader.readLine()).thenReturn("0 @I0001@ INDI", "  ", "1 NAME Elizabeth", "", "1 BIRT", "  ", null);
        String xmlString = xmlFileFormat.generateFormattedOutput(reader);
        assertEquals(outputForEmptyLines(), xmlString);
    }


    @Test
    public void testGenerateFormattedOutputWhenLevelIsInvalid() throws IOException {
        when(reader.readLine()).thenReturn("0 @I0001@ INDI", "NAME Elizabeth", "1 BIRT", null);
        String xmlString = xmlFileFormat.generateFormattedOutput(reader);
        assertEquals(outputForInvalidInput(), xmlString);
    }

    @Test
    public void testGenerateFormattedOutputWhenLevelIsNegative() throws IOException {
        when(reader.readLine()).thenReturn("0 @I0001@ INDI", "-1 NAME Elizabeth", "1 BIRT", null);
        String xmlString = xmlFileFormat.generateFormattedOutput(reader);
        assertEquals(outputForInvalidInput(), xmlString);
    }

    @Test
    public void testGenerateFormattedOutputForNonConsecutiveAscendingLevel() throws IOException {
        when(reader.readLine()).thenReturn("0 @I0001@ INDI", "100 NAME Elizabeth", "1 BIRT", null);
        String xmlString = xmlFileFormat.generateFormattedOutput(reader);
        assertEquals(outputForEmptyLines(), xmlString);
    }

    @Test
    public void testGenerateFormattedOutputForEmptyLevel() throws IOException {
        when(reader.readLine()).thenReturn("0 @I0001@ INDI", "1  ", "1 NAME Elizabeth", "1 BIRT", null);
        String xmlString = xmlFileFormat.generateFormattedOutput(reader);
        assertEquals(outputForEmptyLines(), xmlString);
    }

    @Test
    public void testGenerateFormattedOutputForAttributeWithoutTag() throws IOException {
        when(reader.readLine()).thenReturn("0 @I0001@ INDI", "1 NAME Elizabeth", "1 BIRT", "0 @I0002@", null);
        String xmlString = xmlFileFormat.generateFormattedOutput(reader);
        assertEquals(outputForEmptyLines(), xmlString);
    }

    @Test
    public void testGenerateFormattedOutputWhenBufferedReaderIsNull() throws IOException {
        thrown.expect(IOException.class);
        thrown.expectMessage("Unable to read from input buffer.");
        xmlFileFormat.generateFormattedOutput(null);
    }

    private String outputForEmptyLines() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                "<gedcom>\n" +
                "   <indi id=\"@I0001@\">\n" +
                "      <name>Elizabeth</name>\n" +
                "      <birt/>\n" +
                "   </indi>\n" +
                "</gedcom>\n";
    }

    private String outputForValidData() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                "<gedcom>\n" +
                "   <indi id=\"@I0001@\">\n" +
                "      <name>Elizabeth Alexandra Mary /Windsor/</name>\n" +
                "      <birt>\n" +
                "         <date>21 Apr 1926</date>\n" +
                "      </birt>\n" +
                "      <famc id=\"@F0003@\"/>\n" +
                "      <chan>\n" +
                "         <date>\n" +
                "            <month>Dec</month>\n" +
                "         </date>\n" +
                "      </chan>\n" +
                "   </indi>\n" +
                "   <indi id=\"@I0002@\">\n" +
                "      <name>Philip /Mountbatten/</name>\n" +
                "   </indi>\n" +
                "   <trlr/>\n" +
                "</gedcom>\n";
    }

    private String outputForInvalidInput() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                "<gedcom>\n" +
                "   <indi id=\"@I0001@\">\n" +
                "      <birt/>\n" +
                "   </indi>\n" +
                "</gedcom>\n";
    }

}
