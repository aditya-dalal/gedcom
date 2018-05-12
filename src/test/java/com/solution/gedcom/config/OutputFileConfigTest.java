package com.solution.gedcom.config;

import com.solution.gedcom.groups.UnitTests;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;


@Category(UnitTests.class)
public class OutputFileConfigTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testOutputFileConfigForNullValues() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("xmlConfig");
        new OutputFileConfig(null);
    }

    @Test
    public void testOutputFileConfigForValidValues() {
        OutputFileConfig config = new OutputFileConfig(new OutputFileConfig.XMLConfig("abc", "id", true, 2));
        assertEquals("abc", config.getXmlConfig().getDocumentRoot());
        assertEquals("id", config.getXmlConfig().getAttributeName());
        assertEquals(true, config.getXmlConfig().isIndent());
        assertEquals(2, config.getXmlConfig().getIndentValue());
    }

    @Test
    public void testXMLConfigForNullDocumentRoot() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("documentRoot");
        new OutputFileConfig.XMLConfig(null, null, false, 1);
    }

    @Test
    public void testXMLConfigForNullAttributeName() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("attributeName");
        new OutputFileConfig.XMLConfig("abc", null, false, 1);
    }
}
