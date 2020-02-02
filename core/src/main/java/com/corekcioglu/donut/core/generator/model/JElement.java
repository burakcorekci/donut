package com.corekcioglu.donut.core.generator.model;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public abstract class JElement {
    protected static final String QUOTATION = "\"";
    protected static final String NEW_LINE = "\n";
    protected static final String TAB = "\t";
    protected static final String SEMICOLON = ";" + NEW_LINE;
    protected static final String SPACE = " ";
    protected static final String COMMA = ", ";
    protected static final String OPENING_CURLY = "{";
    protected static final String CLOSING_CURLY = "}";
    protected static final String OPENING_BRACKET = "(";
    protected static final String CLOSING_BRACKET = ")";
    protected static final String STATIC = "static ";
    protected static final String ABSTRACT = "abstract ";

    protected String name;
    @Builder.Default
    protected AccessModifier accessModifier = AccessModifier.PRIVATE;
    @Builder.Default
    protected boolean isStatic = false;
    @Builder.Default
    protected boolean isAbstract = false;

    public abstract List<String> generateLines();

    protected String createCommaSeparatedString(List<String> list) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < list.size(); ++i) {
            builder.append(list.get(i));
            if (i != list.size() - 1) {
                builder.append(COMMA);
            }
        }

        return builder.toString();
    }
}
