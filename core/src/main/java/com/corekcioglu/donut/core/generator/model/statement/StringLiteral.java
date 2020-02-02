package com.corekcioglu.donut.core.generator.model.statement;

import com.corekcioglu.donut.core.generator.model.JStatement;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class StringLiteral extends JStatement {

    private String value;

    @Override
    public List<String> generateLines() {
        return Collections.singletonList(QUOTATION + getValue() + QUOTATION);
    }
}
