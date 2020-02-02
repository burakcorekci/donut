package com.corekcioglu.donut.core.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class MainNameToDonutPutStatement extends JStatement {

    private String constructor;
    @Builder.Default
    private List<GetDonutStatement> getDonutStatements = new ArrayList<>();

    @Override
    public List<String> generateLines() {
        StringBuilder builder = new StringBuilder("mainNameToDonut.put(");

        builder.append(QUOTATION)
                .append(getName())
                .append(QUOTATION)
                .append(COMMA)
                .append(getConstructor())
                .append(OPENING_BRACKET);

        for (int i = 0; i < getGetDonutStatements().size(); ++i) {
            builder.append(getGetDonutStatements().get(i).generateLines().get(0));
            if (i != getGetDonutStatements().size() - 1) {
                builder.append(COMMA);
            }
        }

        builder.append(CLOSING_BRACKET)
                .append(CLOSING_BRACKET);

        return Collections.singletonList(builder.toString());
    }
}
