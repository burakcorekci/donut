package com.corekcioglu.donut.core.generator;

public enum AccessModifier {
    PUBLIC("public"),
    PROTECTED("protected"),
    PRIVATE("private");

    private String value;

    AccessModifier(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return getValue();
    }
}
