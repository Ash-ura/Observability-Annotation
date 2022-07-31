package com.itconsortiumgh.ObservabilityAnnotation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Param {
    private String name;
    private String value;
}