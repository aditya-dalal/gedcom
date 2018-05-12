package com.solution.gedcom.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;


@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class InputFileConfig {
    @NonNull
    private String fieldSeparator;
    @NonNull
    private String attributeIdentifier;
}
