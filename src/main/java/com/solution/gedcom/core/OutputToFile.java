package com.solution.gedcom.core;

import com.google.inject.Inject;
import com.solution.gedcom.annotations.OutputFile;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class OutputToFile implements OutputDestination {

    private String outputFile;

    @Inject
    public OutputToFile(@OutputFile String outputFile) {
        this.outputFile = outputFile;
    }

    @Override
    public void writeToDestination(String output) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
        writer.write(output);
        writer.close();
    }
}
