package com.corekcioglu.donut.core.generator;

import java.util.Collections;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class EmptyStatement extends JStatement {

    @Override
    public List<String> generateLines() {
        return Collections.emptyList();
    }
}
