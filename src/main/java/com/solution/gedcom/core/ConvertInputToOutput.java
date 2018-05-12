package com.solution.gedcom.core;


import com.google.inject.Inject;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConvertInputToOutput {

    private InputSource source;
    private OutputDestination destination;
    private OutputFormat format;

    @Inject
    public ConvertInputToOutput(InputSource source, OutputDestination destination, OutputFormat format) {
        this.source = source;
        this.destination = destination;
        this.format = format;
    }

    public void convertToXML() throws IOException {
        BufferedReader reader = source.readFromSource();
        List<String> inputLines = new ArrayList<>();
        String inputLine;
        while ((inputLine = reader.readLine()) != null) {
            if(isLineEmpty(inputLine))
                continue;
            inputLines.add(inputLine);
            if(inputLines.size() > 3 && inputLine.startsWith("0"))
                processChunk(inputLines);
        }
        inputLines.add("");
        processChunk(inputLines);
        reader.close();
    }

    private void processChunk(List<String> inputLines) {
        List<String> chunk = new ArrayList<>(inputLines);
        inputLines.clear();
        inputLines.add(chunk.remove(chunk.size()-1));
        System.out.println("###chunk");
        for (String s: chunk)
            System.out.println(s);
        System.out.println("###input");
        for(String s: inputLines)
            System.out.println(s);
    }

    private boolean isLineEmpty(String inputLine) {
        return inputLine.isEmpty() || inputLine.trim().length() < 1;
    }
}
