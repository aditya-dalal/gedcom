package com.solution.gedcom;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.solution.gedcom.config.Configuration;
import com.solution.gedcom.groups.UnitTests;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;


@Category(UnitTests.class)
public class GedcomModuleTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private GedcomModule module;

    @Test
    public void testGetConfigurationWhenInvalidConfigFile() throws IOException {
        thrown.expect(FileNotFoundException.class);
        thrown.expectMessage("configFile (No such file or directory)");
        module = new GedcomModule("configFile", "input", "output");
        module.getConfiguration();
    }

    @Test
    public void testGetConfigurationWhenConfigValueIsMissing() throws IOException {
        thrown.expect(JsonMappingException.class);
        thrown.expectMessage("Can not construct instance of com.solution.gedcom.config.InputFileConfig, problem: fieldSeparator");
        String configFile = getClass().getClassLoader().getResource("invalidconfig.yml").getFile();
        module = new GedcomModule(configFile, "input", "output");
        module.getConfiguration();
    }

    @Test
    public void testGetConfiguration() throws IOException {
        String configFile = getClass().getClassLoader().getResource("testconfig.yml").getFile();
        module = new GedcomModule(configFile, "input", "output");
        Configuration configuration = module.getConfiguration();
        assertEquals("\\s+", configuration.getInputFileConfig().getFieldSeparator());
        assertEquals("@", configuration.getInputFileConfig().getAttributeIdentifier());
        assertEquals("id", configuration.getOutputFileConfig().getXmlConfig().getAttributeName());
        assertEquals("gedcom", configuration.getOutputFileConfig().getXmlConfig().getDocumentRoot());
        assertEquals(true, configuration.getOutputFileConfig().getXmlConfig().isIndent());
        assertEquals(3, configuration.getOutputFileConfig().getXmlConfig().getIndentValue());
    }
}
