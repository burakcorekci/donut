package com.corekcioglu.donut.core.generator.model;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
// This is the abstract class for all statements (that have to be one liners).
public abstract class JStatement extends JElement {

    public String generate() {
        List<String> lines = generateLines();
        return lines.isEmpty() ? "" : lines.get(0);
    }
}
