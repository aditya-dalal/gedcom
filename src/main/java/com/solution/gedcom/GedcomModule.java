package com.solution.gedcom;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.solution.gedcom.annotations.InputFile;
import com.solution.gedcom.annotations.OutputFile;
import com.solution.gedcom.config.Configuration;
import com.solution.gedcom.core.*;

import java.io.File;
import java.io.IOException;


public class GedcomModule extends AbstractModule {

    private String configFile;
    private String inputFile;
    private String outputFile;

    public GedcomModule(String configFile, String inputFile, String outputFile) {
        this.configFile = configFile;
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    @Override
    protected void configure() {
        bind(OutputFormat.class).to(XMLFormat.class);
        bind(InputSource.class).to(InputFromFile.class);
        bind(OutputDestination.class).to(OutputToFile.class);
        bindConstant().annotatedWith(InputFile.class).to(inputFile);
        bindConstant().annotatedWith(OutputFile.class).to(outputFile);
    }

    @Singleton
    @Provides
    public Configuration getConfiguration() throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        return mapper.readValue(getConfigFile(), Configuration.class);
    }

    private File getConfigFile() {
        return new File(configFile);
    }

}
