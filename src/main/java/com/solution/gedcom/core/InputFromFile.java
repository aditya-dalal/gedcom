package com.solution.gedcom.core;

import com.google.inject.Inject;
import com.solution.gedcom.annotations.InputFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;


public class InputFromFile implements InputSource {

    private String inputFile;
    private static final Logger LOGGER = LoggerFactory.getLogger(InputFromFile.class);

    @Inject
    public InputFromFile(@InputFile String inputFile) {
        this.inputFile = inputFile;
    }

    @Override
    public BufferedReader readFromSource() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(inputFile));
        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return reader;
    }
}
