package com.corekcioglu.donut.core.generator;

import java.util.Collections;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class JField extends JElement {
    private String type;

    @Override
    public List<String> generateLines() {
        return Collections.singletonList(new StringBuilder()
                .append(getAccessModifier())
                .append(SPACE)
                .append(getType())
                .append(SPACE)
                .append(getName())
                .append(SEMICOLON)
                .toString());
    }
}
