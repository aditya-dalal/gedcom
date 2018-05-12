package com.solution.gedcom.core;

import com.solution.gedcom.groups.UnitTests;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.BufferedReader;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


@Category(UnitTests.class)
public class InputFromFileTest {

    private InputFromFile input;

    @Test
    public void testReadFromSource() throws IOException {
        String testFile = getClass().getClassLoader().getResource("sample.txt").getFile();
        input = new InputFromFile(testFile);
        BufferedReader reader = input.readFromSource();
        assertNotNull(reader);
        assertEquals("0 @I0001@ INDI", reader.readLine());
        reader.close();
    }

    @Test
    public void testReadFromSOurceWhenInvalidFile() throws IOException {
        input = new InputFromFile("abc");
        BufferedReader reader = input.readFromSource();
        assertNull(reader);
    }
}
