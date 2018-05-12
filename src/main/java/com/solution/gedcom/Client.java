package com.solution.gedcom;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.solution.gedcom.core.*;

import java.io.IOException;


public class Client {

    public static void main(String[] args) throws IOException {
        if(args.length < 3) {
            System.out.println("Usage: <config file> <input file> <output file>");
            System.exit(1);
        }
        String configFile = args[0];
        String inputFile = args[1];
        String outputFile = args[2];
        Injector injector = Guice.createInjector(new GedcomModule(configFile, inputFile, outputFile));

        OutputFormat xmlFileFormat = injector.getInstance(XMLFormat.class);
        InputSource input = injector.getInstance(InputFromFile.class);
        OutputDestination output = injector.getInstance(OutputToFile.class);

        String xmlString = xmlFileFormat.generateFormattedOutput(input.readFromSource());
        output.writeToDestination(xmlString);

        ConvertInputToOutput covert = injector.getInstance(ConvertInputToOutput.class);
        covert.convertToXML();
    }

}
