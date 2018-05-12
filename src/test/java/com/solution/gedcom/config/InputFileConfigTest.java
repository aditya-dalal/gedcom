package com.solution.gedcom.config;

import com.solution.gedcom.groups.UnitTests;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;


@Category(UnitTests.class)
public class InputFileConfigTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testInputFileConfigForNullFieldSeparator() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("fieldSeparator");
        new InputFileConfig(null, null);
    }

    @Test
    public void testInputFileConfigForNullAttributeIdentifier() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("attributeIdentifier");
        new InputFileConfig("\\s+", null);
    }

    @Test
    public void testInputFileConfigForValidValues() {
        InputFileConfig config = new InputFileConfig("\\s+", "@");
        assertEquals("\\s+", config.getFieldSeparator());
        assertEquals("@", config.getAttributeIdentifier());
    }
}
