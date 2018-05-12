package com.solution.gedcom.config;

import com.solution.gedcom.groups.UnitTests;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;


@Category(UnitTests.class)
public class ConfigurationTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testConfigurationForNullInputFileConfig() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("inputFileConfig");
        new Configuration(null, null);
    }

    @Test
    public void testConfigurationForNullOutputFileConfig() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("outputFileConfig");
        new Configuration(inputFileConfig(), null);
    }

    @Test
    public void testConfigurationForValidValues() {
        Configuration config = new Configuration(inputFileConfig(), getOutputFileConfig());
        assertEquals("a", config.getInputFileConfig().getFieldSeparator());
        assertEquals("gedcom", config.getOutputFileConfig().getXmlConfig().getDocumentRoot());
    }

    private InputFileConfig inputFileConfig() {
        return new InputFileConfig("a", "b");
    }

    private OutputFileConfig getOutputFileConfig() {
        return new OutputFileConfig(new OutputFileConfig.XMLConfig("gedcom", "id", true, 3));
    }
}
