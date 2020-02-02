package com.corekcioglu.donut.core.generator.model;

import com.corekcioglu.donut.core.generator.model.AccessModifier;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
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

    private String name;
    @Builder.Default
    private AccessModifier accessModifier = AccessModifier.PRIVATE;
    @Builder.Default
    private boolean isStatic = false;
    @Builder.Default
    private boolean isAbstract = false;

    public abstract List<String> generateLines();
}
