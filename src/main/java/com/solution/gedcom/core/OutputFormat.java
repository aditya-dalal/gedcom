package com.solution.gedcom.core;

import java.io.BufferedReader;
import java.io.IOException;


public interface OutputFormat {
    String generateFormattedOutput(BufferedReader inputBuffer) throws IOException;
}
