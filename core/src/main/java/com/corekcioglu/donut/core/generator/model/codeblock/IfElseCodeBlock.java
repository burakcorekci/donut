package com.corekcioglu.donut.core.generator.model.codeblock;

import static java.util.Objects.nonNull;

import com.corekcioglu.donut.core.generator.model.JCodeBlock;
import com.corekcioglu.donut.core.generator.model.JStatement;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class IfElseCodeBlock extends JCodeBlock {
    private JCodeBlock ifBlock;
    private JCodeBlock elseBlock;
    private JStatement ifStatement;

    @Override
    public List<String> generateLines() {
        List<String> lines = new ArrayList<>();

        StringBuilder builder = new StringBuilder("if ");
        builder.append(OPENING_BRACKET)
                .append(ifStatement.generate())
                .append(CLOSING_BRACKET)
                .append(SPACE)
                .append(OPENING_CURLY)
                .append(NEW_LINE);
        lines.add(builder.toString());

        if (nonNull(getIfBlock())) {
            for (String line : getIfBlock().generateLines()) {
                lines.add(TAB + line);
            }
        }

        if (nonNull(getElseBlock())) {
            lines.add(CLOSING_CURLY + "else " + OPENING_CURLY + NEW_LINE);

            for (String line : getElseBlock().generateLines()) {
                lines.add(TAB + line);
            }
        }
        lines.add(CLOSING_CURLY + NEW_LINE);

        return lines;
    }
}
