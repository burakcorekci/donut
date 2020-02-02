package com.corekcioglu.donut.core.generator.model;

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
public class JCodeBlock extends JElement {
    protected List<JCodeBlock> blocks = new ArrayList<>();

    public List<String> generateLines() {
        List<String> lines = new ArrayList<>();
        for (JCodeBlock block : blocks) {
            lines.addAll(block.generateLines());
        }
        return lines;
    }
}
