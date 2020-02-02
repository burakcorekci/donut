package com.corekcioglu.donut.core.generator.model.statement;

import com.corekcioglu.donut.core.generator.model.JStatement;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public abstract class OperatorStatement extends JStatement {

    private String operator;
}
