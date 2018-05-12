package com.solution.gedcom.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;


@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class OutputFileConfig {
    @NonNull
    private XMLConfig xmlConfig;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    @AllArgsConstructor
    public static class XMLConfig {
        @NonNull
        private String documentRoot;
        @NonNull
        private String attributeName;
        @Setter
        private boolean indent;
        private int indentValue;
    }
}
