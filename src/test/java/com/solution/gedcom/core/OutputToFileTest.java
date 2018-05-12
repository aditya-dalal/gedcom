package com.solution.gedcom.core;

import com.solution.gedcom.groups.UnitTests;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;


@Category(UnitTests.class)
public class OutputToFileTest {

    private OutputToFile output;

    @Test
    public void testWriteToDestination() throws IOException {
        String outputFile = "/tmp/" + System.currentTimeMillis() + ".xml";
        String text = "test string";
        output = new OutputToFile(outputFile);
        output.writeToDestination(text);
        File file = new File(outputFile);
        assertTrue(file.exists() && !file.isDirectory());
        file.delete();
    }
}
