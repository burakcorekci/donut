package com.corekcioglu.donut.core.generator.model.statement;

import com.corekcioglu.donut.core.generator.model.JStatement;
import java.util.Collections;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class ReturnStatement extends JStatement {
    private JStatement statement;

    @Override
    public List<String> generateLines() {
        return Collections.singletonList("return " + statement.generate());
    }
}
