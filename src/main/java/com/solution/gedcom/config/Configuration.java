package com.solution.gedcom.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;


@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class Configuration {
    @NonNull
    private InputFileConfig inputFileConfig;
    @NonNull
    private OutputFileConfig outputFileConfig;
}
