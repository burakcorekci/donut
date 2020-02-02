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
public class NameToMainNamePutStatement extends JStatement {

    private String mainName;

    @Override
    public final List<String> generateLines() {
        return Collections.singletonList(
                "nameToMainName.put(" + QUOTATION + getName() + QUOTATION + COMMA + QUOTATION
                        + getMainName() + QUOTATION + CLOSING_BRACKET);
    }
}
