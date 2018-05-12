package com.solution.gedcom.core;

import com.solution.gedcom.config.Configuration;
import com.solution.gedcom.config.InputFileConfig;
import com.solution.gedcom.config.OutputFileConfig;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;


public class BaseXMLTest {

    @Mock
    protected Configuration configuration;
    @Mock
    protected OutputFileConfig outputFileConfig;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(configuration.getInputFileConfig()).thenReturn(getInputFileConfig());
        when(configuration.getOutputFileConfig()).thenReturn(outputFileConfig);
        when(outputFileConfig.getXmlConfig()).thenReturn(getXmlConfig());
    }

    private OutputFileConfig.XMLConfig getXmlConfig() {
        return new OutputFileConfig.XMLConfig("gedcom", "id", true, 3);
    }

    private InputFileConfig getInputFileConfig() {
        return new InputFileConfig("\\s+", "@");
    }

    protected String gedcomOutputWithoutData() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<gedcom/>\n";
    }
}
