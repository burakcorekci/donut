package com.corekcioglu.donut.core.generator.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Argument {
    private String type;
    private String name;
}
