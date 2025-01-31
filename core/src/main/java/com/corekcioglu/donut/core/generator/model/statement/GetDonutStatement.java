package com.corekcioglu.donut.core.generator.model.statement;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class GetDonutStatement extends FunctionCallStatement {

    @Builder.Default
    protected String name = "getDonut";
}
