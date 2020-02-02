package com.corekcioglu.donut.core.generator.model;

import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class JClass extends JElement {

    protected static final String CLASS = "class ";
    protected static final String PACKAGE = "package ";
    protected static final String IMPORT = "import ";
    protected static final String EXTENDS = "extends ";
    protected static final String IMPLEMENTS = "implements ";

    private String packageName;
    @Builder.Default
    private List<String> importedClassNames = new ArrayList<>();
    private String extendedClassName;
    @Builder.Default
    private List<String> implementedClassNames = new ArrayList<>();
    @Builder.Default
    private List<JField> fields = new ArrayList<>();
    @Builder.Default
    private List<JMethod> methods = new ArrayList<>();

    @Override
    public final List<String> generateLines() {
        List<String> lines = new ArrayList<>();
        lines.add(PACKAGE + getPackageName() + SEMICOLON);
        lines.add(NEW_LINE);

        importedClassNames.add(extendedClassName);
        importedClassNames.addAll(implementedClassNames);
        importedClassNames.forEach(importedClassName ->
                lines.add(IMPORT + importedClassName + SEMICOLON + NEW_LINE));

        StringBuilder builder = new StringBuilder();
        builder.append(getAccessModifier())
                .append(SPACE);
        if (isStatic()) {
            builder.append(STATIC);
        }
        if (isAbstract()) {
            builder.append(ABSTRACT);
        }
        builder.append(CLASS)
                .append(getName())
                .append(SPACE);

        if (nonNull(getExtendedClassName())) {
            builder.append(EXTENDS)
                    .append(getSimpleClassNameFromFullName(getExtendedClassName()))
                    .append(SPACE);
        }

        if (!getImplementedClassNames().isEmpty()) {
            builder.append(IMPLEMENTS);
            for (int i = 0; i < getImplementedClassNames().size(); ++i) {
                builder.append(getSimpleClassNameFromFullName(getImplementedClassNames().get(i)));
                if (i != getImplementedClassNames().size() - 1) {
                    builder.append(COMMA);
                }
                builder.append(SPACE);
            }
        }
        builder.append(OPENING_CURLY + NEW_LINE);
        lines.add(builder.toString());

        for (JField field : getFields()) {
            List<String> strs = field.generateLines();
            for (String str : strs) {
                lines.add(TAB + str);
            }
        }

        lines.add(NEW_LINE);

        for (JMethod method : getMethods()) {
            List<String> strs = method.generateLines();
            for (String str : strs) {
                lines.add(TAB + str);
            }
        }

        lines.add(CLOSING_CURLY);

        return lines;
    }

    private String getSimpleClassNameFromFullName(String fullName) {
        int lastDotIndex = fullName.lastIndexOf(".");
        return fullName.substring(lastDotIndex + 1);
    }
}
