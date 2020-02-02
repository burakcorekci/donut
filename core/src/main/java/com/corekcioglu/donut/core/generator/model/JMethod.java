package com.corekcioglu.donut.core.generator.model;

import com.corekcioglu.donut.core.generator.model.statement.EmptyStatement;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class JMethod extends JElement {
    private String returnType;
    @Builder.Default
    private List<Argument> arguments = new ArrayList<>();
    @Builder.Default
    private List<JStatement> statements = new ArrayList<>();

    @Override
    public List<String> generateLines() {
        List<String> lines = new ArrayList<>();

        StringBuilder builder = new StringBuilder();
        builder.append(getAccessModifier())
                .append(SPACE);

        if (isAbstract()) {
            builder.append(ABSTRACT);
        }
        if (isStatic()) {
            builder.append(STATIC);
        }

        builder.append(returnType)
                .append(SPACE)
                .append(getName())
                .append(OPENING_BRACKET);

        for (int i = 0; i < getArguments().size(); ++i) {
            Argument argument = getArguments().get(i);
            builder.append(argument.getType())
                    .append(SPACE)
                    .append(argument.getName());
            if (i != getArguments().size() - 1) {
                builder.append(COMMA);
            }
        }
        builder.append(CLOSING_BRACKET)
                .append(SPACE)
                .append(OPENING_CURLY)
                .append(NEW_LINE);
        lines.add(builder.toString());

        for (JStatement statement : getStatements()) {
            if (statement.getClass() == EmptyStatement.class) {
                lines.add(TAB + NEW_LINE);
            } else {
                lines.add(TAB + statement.generateLines().get(0) + SEMICOLON);
            }
        }

        lines.add(CLOSING_CURLY + NEW_LINE);

        return lines;
    }

    @Builder
    @Data
    public static class Argument {
        private String type;
        private String name;
    }
}
