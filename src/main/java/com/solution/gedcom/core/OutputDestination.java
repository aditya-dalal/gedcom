package com.solution.gedcom.core;

import java.io.IOException;


public interface OutputDestination {
    void writeToDestination(String output) throws IOException;
}
